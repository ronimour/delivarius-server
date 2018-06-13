package com.delivarius.spring.server.delivariusserver.service.resource;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.delivarius.spring.server.delivariusserver.domain.Order;
import com.delivarius.spring.server.delivariusserver.domain.Store;
import com.delivarius.spring.server.delivariusserver.domain.User;
import com.delivarius.spring.server.delivariusserver.domain.exception.LoginConflictException;
import com.delivarius.spring.server.delivariusserver.domain.helper.HistoryStatusOrderHelper;
import com.delivarius.spring.server.delivariusserver.repository.StoreRepository;
import com.delivarius.spring.server.delivariusserver.repository.UserRepository;
import com.delivarius.spring.server.delivariusserver.service.dto.OrderDto;
import com.delivarius.spring.server.delivariusserver.service.dto.StoreDto;
import com.delivarius.spring.server.delivariusserver.service.dto.UserDto;
import com.delivarius.spring.server.delivariusserver.service.dto.mapper.ModelMapperHelper;
import com.delivarius.spring.server.delivariusserver.service.dto.mapper.exception.MapperConvertDtoException;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserResource extends AbstractResource{

	@Autowired
	private UserRepository userRepository;
	
	@PostMapping(path = "/create", consumes="application/json", produces= {"application/json"})
	@ResponseStatus(code=HttpStatus.CREATED)
	public UserDto createUser(@Valid @RequestBody UserDto userDto, @NotEmpty @RequestBody String password) throws MapperConvertDtoException, LoginConflictException {
		
		String login = userDto.getLogin();
		
		if(userRepository.findByLogin(login).isEmpty()) {
			User user = (User) ModelMapperHelper.getInstance().convert(User.class, userDto);
			user.setPassword(password);
			user.setRegistrationDate(LocalDateTime.now());
			userRepository.save(user);			
			userDto = (UserDto) ModelMapperHelper.getInstance().convert(User.class, user);
			
		} else {
			throw new LoginConflictException();
		}
		
		return null;
	}
	
	@PostMapping(path = "/update", consumes="application/json", produces= {"application/json"})
	@ResponseStatus(code=HttpStatus.OK)
	public void udpateUser(@Valid @RequestBody UserDto userDto) throws MapperConvertDtoException, LoginConflictException {
		
		User user = (User) ModelMapperHelper.getInstance().convert(User.class, userDto);
		
		userRepository.save(user);
		
	}
	
	@ExceptionHandler(LoginConflictException.class)
	public void handleMapperConvert(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "conflict");
	}
}
