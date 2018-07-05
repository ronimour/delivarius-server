package com.delivarius.server.spring.service.resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.delivarius.server.general.OptionalUtils;
import com.delivarius.server.spring.domain.ItemOrder;
import com.delivarius.server.spring.domain.Order;
import com.delivarius.server.spring.domain.Product;
import com.delivarius.server.spring.domain.ProductStock;
import com.delivarius.server.spring.domain.Store;
import com.delivarius.server.spring.domain.exception.EntityNotFoundException;
import com.delivarius.server.spring.domain.exception.InvalidOperationException;
import com.delivarius.server.spring.domain.exception.NegativeAmountException;
import com.delivarius.server.spring.domain.helper.HistoryStatusOrderHelper;
import com.delivarius.server.spring.domain.utils.OrderUtils;
import com.delivarius.server.spring.repository.ItemOrderRepository;
import com.delivarius.server.spring.repository.OrderRepository;
import com.delivarius.server.spring.repository.ProductRepository;
import com.delivarius.server.spring.repository.ProductStockRepository;
import com.delivarius.server.spring.service.dto.ItemOrderDto;
import com.delivarius.server.spring.service.dto.OrderDto;
import com.delivarius.server.spring.service.dto.mapper.ModelMapperHelper;
import com.delivarius.server.spring.service.dto.mapper.exception.MapperConvertDtoException;

@RestController
@RequestMapping("/order")
@CrossOrigin
@Transactional
public class OrderResource extends AbstractResource {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ItemOrderRepository itemOrderRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductStockRepository productStockRepository;

	@GetMapping(path = "/user/{userId}", produces = { "application/json" })
	public List<OrderDto> getOrders(@PathVariable Long userId) throws MapperConvertDtoException {
		List<Order> orders = orderRepository.findByUserId(userId);
		List<OrderDto> ordersDto = new ArrayList<>();

		for (Order order : orders) {
			OrderDto dto = (OrderDto) modelMapperHelper.convert(Order.class, order);
			ordersDto.add(dto);
		}

		return ordersDto;
	}

	@PostMapping(path = "/create", consumes = "application/json", produces = { "application/json" })
	@ResponseStatus(code = HttpStatus.OK)
	public OrderDto createOrder(@Valid @RequestBody OrderDto orderDto)
			throws MapperConvertDtoException, NegativeAmountException, EntityNotFoundException, InvalidOperationException {

		Order order = (Order) modelMapperHelper.convert(Order.class, orderDto);
		if(CollectionUtils.isEmpty(order.getItems()))
			throw new InvalidOperationException();
		
		Store store = order.getStore();

		for (ItemOrder item : order.getItems()) {
			subtractAmountFromProductStock(store, item.getProduct(), item.getAmount());
		}

		order.getHistory().add(HistoryStatusOrderHelper.getHistoryForOpen(order, order.getUser()));
		order = orderRepository.save(order);

		return (OrderDto) modelMapperHelper.convert(Order.class, order);
	}

	@GetMapping(path = "/confirm/{orderId}", produces = { "application/json" })
	@ResponseStatus(code = HttpStatus.OK)
	public OrderDto confirmOrder(@PathVariable Long orderId) throws MapperConvertDtoException, InvalidOperationException {
		Optional<Order> optOrder = orderRepository.findById(orderId);
		OrderDto orderDto = null;
		if (optOrder.isPresent()) {
			Order order = optOrder.get();
			if(OrderUtils.isOrderOpen(order)) {
				order.getHistory().add(HistoryStatusOrderHelper.getHistoryForRegister(order, order.getUser()));
				orderRepository.save(order);
				orderDto = (OrderDto) ModelMapperHelper.getInstance().convert(Order.class, order);
			} else {
				throw new InvalidOperationException();
			}
		}

		return orderDto;
	}

	@PostMapping(path = "/item/add", consumes = "application/json", produces = { "application/json" })
	@ResponseStatus(code = HttpStatus.OK)
	public ItemOrderDto addItem(@Valid @RequestBody ItemOrderDto itemDto) throws InvalidOperationException, MapperConvertDtoException, NegativeAmountException, EntityNotFoundException {

		Optional<Product> optProduct = productRepository.findById(itemDto.getProductDto().getId());
		Optional<Order> optOrder = orderRepository.findById(itemDto.getOrderId());
		if (OptionalUtils.isAllPresent(optOrder, optProduct)) {
			Product product = optProduct.get();
			Order order = optOrder.get();
			if (OrderUtils.isOrderOpen(order)) {
				Store store = order.getStore();
				ItemOrder item = (ItemOrder) ModelMapperHelper.getInstance().convert(ItemOrder.class, itemDto);
				subtractAmountFromProductStock(store, product, item.getAmount());
				item.setOrder(order);
				item.setProduct(product);
				itemOrderRepository.save(item);
				itemDto = (@Valid ItemOrderDto) ModelMapperHelper.getInstance().convert(ItemOrder.class, item);
			} else {
				throw new InvalidOperationException();
			}
		}

		return itemDto;
	}

	@DeleteMapping(path = "/item/{itemId}")
	@ResponseStatus(code = HttpStatus.OK)
	public void removeItem(@PathVariable Long itemId) throws EntityNotFoundException {
		Optional<ItemOrder>  optItemOrder = itemOrderRepository.findById(itemId);
		if(optItemOrder.isPresent()) {
			ItemOrder itemOrder = optItemOrder.get();
			addAmountToProductStock(itemOrder.getOrder().getStore(), itemOrder.getProduct(), itemOrder.getAmount());
			itemOrderRepository.delete(itemOrder);
		} else {
			throw new EntityNotFoundException();
		}

	}

	@GetMapping(path = "/item/increase/{itemId}", produces = "application/json")
	@ResponseStatus(code = HttpStatus.OK)
	public void increaseItem(@PathVariable Long itemId,
			@RequestParam(name = "amount", defaultValue = "0") Integer amount) throws NegativeAmountException, EntityNotFoundException {

		Optional<ItemOrder>  optItemOrder = itemOrderRepository.findById(itemId);
		if(optItemOrder.isPresent()) {
			ItemOrder itemOrder = optItemOrder.get();
			subtractAmountFromProductStock(itemOrder.getOrder().getStore(), itemOrder.getProduct(), amount);
			itemOrder.setAmount(itemOrder.getAmount()+amount);
			itemOrderRepository.save(itemOrder);
		} else {
			throw new EntityNotFoundException();
		}
	}

	@GetMapping(path = "/item/decrease/{itemId}", produces = "application/json")
	@ResponseStatus(code = HttpStatus.OK)
	public void decreaseItem(@PathVariable Long itemId,
			@RequestParam(name = "amount", defaultValue = "0") Integer amount) throws EntityNotFoundException {
		
		Optional<ItemOrder>  optItemOrder = itemOrderRepository.findById(itemId);
		if(optItemOrder.isPresent()) {
			ItemOrder itemOrder = optItemOrder.get();
			addAmountToProductStock(itemOrder.getOrder().getStore(), itemOrder.getProduct(), amount);
			itemOrder.setAmount(itemOrder.getAmount()-amount);
			itemOrderRepository.save(itemOrder);
		} else {
			throw new EntityNotFoundException();
		}
	}

	private synchronized void subtractAmountFromProductStock(Store store, Product product, Integer amount)
			throws NegativeAmountException, EntityNotFoundException {
		ProductStock productStock = productStockRepository.findByStoreAndProdcut(store.getId(), product.getId());
		if (productStock == null)
			throw new EntityNotFoundException();
		productStock.setAmount(productStock.getAmount() + amount);
		try {
			productStockRepository.save(productStock);
		} catch (Exception e) {
			throw new NegativeAmountException(e);
		}
	}

	private synchronized void addAmountToProductStock(Store store, Product product, Integer amount)
			throws EntityNotFoundException {
		ProductStock productStock = productStockRepository.findByStoreAndProdcut(store.getId(), product.getId());
		if (productStock == null)
			throw new EntityNotFoundException();
		productStock.setAmount(productStock.getAmount() + amount);
		productStockRepository.save(productStock);

	}

	@ExceptionHandler(InvalidOperationException.class)
	public void handleInvalidOperationException(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "invalid operation");
	}
	
	@ExceptionHandler(NegativeAmountException.class)
	public void handleNegativeAmountException(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "negative amount");
	}
	
}
