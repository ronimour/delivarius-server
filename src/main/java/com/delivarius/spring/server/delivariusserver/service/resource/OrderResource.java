package com.delivarius.spring.server.delivariusserver.service.resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Optionals;
import org.springframework.http.HttpStatus;
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

import com.delivarius.spring.server.delivariusserver.domain.ItemOrder;
import com.delivarius.spring.server.delivariusserver.domain.Order;
import com.delivarius.spring.server.delivariusserver.domain.Product;
import com.delivarius.spring.server.delivariusserver.domain.ProductStock;
import com.delivarius.spring.server.delivariusserver.domain.Store;
import com.delivarius.spring.server.delivariusserver.domain.helper.HistoryStatusOrderHelper;
import com.delivarius.spring.server.delivariusserver.repository.ItemOrderRepository;
import com.delivarius.spring.server.delivariusserver.repository.OrderRepository;
import com.delivarius.spring.server.delivariusserver.repository.ProductRepository;
import com.delivarius.spring.server.delivariusserver.repository.ProductStockRepository;
import com.delivarius.spring.server.delivariusserver.repository.StoreRepository;
import com.delivarius.spring.server.delivariusserver.service.dto.ItemOrderDto;
import com.delivarius.spring.server.delivariusserver.service.dto.OrderDto;
import com.delivarius.spring.server.delivariusserver.service.dto.mapper.ModelMapperHelper;
import com.delivarius.spring.server.delivariusserver.service.dto.mapper.exception.MapperConvertDtoException;

import general.OptionalUtils;

@RestController
@RequestMapping("/order")
@CrossOrigin
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
	
	private static final Object lock = new Object();
	
	
	@GetMapping(path = "/user", produces= {"application/json"})
	public List<OrderDto> getOrders(@RequestParam Long userId) throws MapperConvertDtoException{
		List<Order> orders = orderRepository.findByUserId(userId);
		List<OrderDto> storesDto = new ArrayList<>();
		
		for(Order order : orders) {
			OrderDto dto = (OrderDto) modelMapperHelper.convert(Order.class, order); 
			storesDto.add(dto);
		}
		
		return storesDto;
	}
	@PostMapping(path = "/create", consumes="application/json", produces= {"application/json"})
	@ResponseStatus(code=HttpStatus.CREATED)
	public OrderDto createOrder(@Valid @RequestBody OrderDto orderDto) throws MapperConvertDtoException {
		
		Order order = (Order) modelMapperHelper.convert(Order.class, orderDto);
		order.getHistory().add(HistoryStatusOrderHelper.getHistoryForOpen(order, order.getUser()));
		order = orderRepository.save(order);
		
		return (OrderDto) modelMapperHelper.convert(Order.class, order);
	}
	
	@PostMapping(path = "/add", consumes="application/json",produces= {"application/json"})
	@ResponseStatus(code=HttpStatus.CREATED)
	public ItemOrderDto addItem(@Valid @RequestBody ItemOrderDto itemDto, @RequestBody Long storeId, @RequestBody Long orderId) throws Exception {
		
		synchronized (lock) {
			Optional<Product> optProduct = productRepository.findById(itemDto.getProductDto().getId());
			Optional<Store> optStore = storeRepository.findById(storeId);
			Optional<Order> optOrder = orderRepository.findById(orderId);
			
			if(OptionalUtils.isAllPresent(optOrder,optProduct,optStore)) {
				Product product = optProduct.get();
				Store store = optStore.get();
				Order order = optOrder.get();
				
				Optional<ProductStock> optProdStock = store.getProductsStock()
						.stream()
						.filter( ps -> ps.getProduct().equals(product))
						.findFirst();
				if(optProdStock.isPresent()) {
					ProductStock prodStock = optProdStock.get();
					if(prodStock.getAmount() > 0 ) {
						ItemOrder item = new ItemOrder();
						item.setAmount(1);
						item.setProduct(product);
						item.setTotalPrice(prodStock.getPrice());
						item.setOrder(order);
						
						itemOrderRepository.save(item);
						prodStock.setAmount(prodStock.getAmount()-1);
						productStockRepository.save(prodStock);
						
						itemDto = (@Valid ItemOrderDto) ModelMapperHelper.getInstance().convert(ItemOrder.class, item);
					}
				}
				
			}
		}
		
		return itemDto;
	}
	
	@DeleteMapping(consumes="application/json")
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	public void removeItem(@Valid @RequestBody ItemOrderDto itemDto) {
		///tarefaRepository.deleteById(idTarefa);
	}
	
	@PutMapping(path = "/increase", consumes="application/json",produces="application/json")
	@ResponseStatus(code=HttpStatus.OK)
	public ItemOrderDto increaseItem(@Valid @RequestBody ItemOrderDto itemOrderDto){
		return null;
	}
	
	@PutMapping(path = "/decrease",consumes="application/json",produces="application/json")
	@ResponseStatus(code=HttpStatus.OK)
	public ItemOrderDto decreaseItem(@Valid @RequestBody ItemOrderDto itemOrderDto){
		return null;
	}
	
	
	@ExceptionHandler(MapperConvertDtoException.class)
	public void handleMapperConvert(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "parse error");
	}
	
}
