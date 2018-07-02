package com.delivarius.server.spring.service.resource;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
import com.delivarius.server.spring.domain.StatusOrder;
import com.delivarius.server.spring.domain.Store;
import com.delivarius.server.spring.domain.exception.InvalidStatusOrderException;
import com.delivarius.server.spring.domain.helper.HistoryStatusOrderHelper;
import com.delivarius.server.spring.repository.ItemOrderRepository;
import com.delivarius.server.spring.repository.OrderRepository;
import com.delivarius.server.spring.repository.ProductRepository;
import com.delivarius.server.spring.repository.ProductStockRepository;
import com.delivarius.server.spring.repository.StoreRepository;
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
	
	@Autowired
	private StoreRepository storeRepository;
	
	@GetMapping(path = "/user/{userId}", produces= {"application/json"})
	public List<OrderDto> getOrders(@PathVariable Long userId) throws MapperConvertDtoException{
		List<Order> orders = orderRepository.findByUserId(userId);
		List<OrderDto> ordersDto = new ArrayList<>();
		
		for(Order order : orders) {
			OrderDto dto = (OrderDto) modelMapperHelper.convert(Order.class, order); 
			ordersDto.add(dto);
		}
		
		return ordersDto;
	}
	@PostMapping(path = "/create", consumes="application/json", produces= {"application/json"})
	@ResponseStatus(code=HttpStatus.CREATED)
	public OrderDto createOrder(@Valid @RequestBody OrderDto orderDto) throws MapperConvertDtoException {
		
		Order order = (Order) modelMapperHelper.convert(Order.class, orderDto);
		
		order.getHistory().add(HistoryStatusOrderHelper.getHistoryForOpen(order, order.getUser()));
		order = orderRepository.save(order);
		
		return (OrderDto) modelMapperHelper.convert(Order.class, order);
	}
	
	@GetMapping(path = "/confirm/{orderId}", produces= {"application/json"} )
	public OrderDto confirmOrder(@PathVariable Long orderId ) throws MapperConvertDtoException, InvalidStatusOrderException {
		Optional<Order> optOrder = orderRepository.findById(orderId);
		OrderDto orderDto = null;
		if(optOrder.isPresent()) {
			Order order = optOrder.get();
			if(order.getHistory().size() == 1 && order.getHistory().get(0).getStatus().equals(StatusOrder.OPENED)) {
				order.getHistory().add(HistoryStatusOrderHelper.getHistoryForRegister(order, order.getUser()));
				orderRepository.save(order);
				orderDto = (OrderDto) ModelMapperHelper.getInstance().convert(Order.class, order);
			} else {
				throw new InvalidStatusOrderException();
			}
		}
		
		return orderDto;
	}
	
	@PostMapping(path = "/add", consumes="application/json",produces= {"application/json"})
	@ResponseStatus(code=HttpStatus.CREATED)
	public ItemOrderDto addItem(@Valid @RequestBody ItemOrderDto itemDto) throws Exception {

		Optional<Product> optProduct = productRepository.findById(itemDto.getProductDto().getId());
		Optional<Order> optOrder = orderRepository.findById(itemDto.getOrderId());
		if (OptionalUtils.isAllPresent(optOrder, optProduct)) {
			Product product = optProduct.get();
			Order order = optOrder.get();
			Store store = order.getStore();
			Optional<ProductStock> optProdStock = store.getProductsStock().stream()
					.filter(ps -> ps.getProduct().equals(product)).findFirst();
			if (optProdStock.isPresent()) {
				ProductStock prodStock = optProdStock.get();
				ItemOrder item = (ItemOrder) ModelMapperHelper.getInstance().convert(ItemOrder.class, itemDto);
				prodStock.setAmount(prodStock.getAmount() - item.getAmount());

				productStockRepository.save(prodStock);

				item.setOrder(order);
				item.setProduct(product);
				item.setTotalPrice(prodStock.getPrice().multiply(new BigDecimal(item.getAmount())));

				itemOrderRepository.save(item);

				itemDto = (@Valid ItemOrderDto) ModelMapperHelper.getInstance().convert(ItemOrder.class, item);
			}

		}

		return itemDto;
	}
	
	@DeleteMapping( path= "/item/delete/{itemId}")
	@ResponseStatus(code=HttpStatus.OK)
	public void removeItem(@PathVariable Long itemId) {
		
	}
	
	@GetMapping(path = "/item/increase/{itemId}", produces="application/json")
	@ResponseStatus(code=HttpStatus.OK)
	public ItemOrderDto increaseItem(@PathVariable Long itemId, @RequestParam(name="amount",defaultValue="0") Integer amount){
		
		
		return null;
	}
	
	@GetMapping(path = "/item/decrease/{itemId}",produces="application/json")
	@ResponseStatus(code=HttpStatus.OK)
	public ItemOrderDto decreaseItem(@Valid @RequestBody ItemOrderDto itemOrderDto){
		return null;
	}
	
	
	@ExceptionHandler(Exception.class)
	public void handleException(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "unknown error");
	}
	
	@ExceptionHandler(MapperConvertDtoException.class)
	public void handleMapperConvert(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "parse error");
	}
	
}
