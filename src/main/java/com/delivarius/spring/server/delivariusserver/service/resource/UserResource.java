package com.delivarius.spring.server.delivariusserver.service.resource;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.delivarius.spring.server.delivariusserver.domain.User;
import com.delivarius.spring.server.delivariusserver.domain.UserType;
import com.delivarius.spring.server.delivariusserver.domain.exception.EntityNotFoundException;
import com.delivarius.spring.server.delivariusserver.domain.exception.LoginConflictException;
import com.delivarius.spring.server.delivariusserver.domain.exception.LoginFailException;
import com.delivarius.spring.server.delivariusserver.repository.UserRepository;
import com.delivarius.spring.server.delivariusserver.service.dto.LoginDto;
import com.delivarius.spring.server.delivariusserver.service.dto.UserDto;
import com.delivarius.spring.server.delivariusserver.service.dto.mapper.ModelMapperHelper;
import com.delivarius.spring.server.delivariusserver.service.dto.mapper.exception.MapperConvertDtoException;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserResource extends AbstractResource{

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping(path="/{idUser}",produces={"application/json"})
	public UserDto recuperarTarefa(@PathVariable Long idUser) throws MapperConvertDtoException{
		UserDto userDto = new UserDto();
		Optional<User> user = userRepository.findById(idUser);
		if(user.isPresent()) {
			userDto = (UserDto) ModelMapperHelper.getInstance().convert(User.class, user.get());
		}		
		return userDto;
	}
	
	@PostMapping(path = "/login", consumes="application/json", produces= {"application/json"})
	@ResponseStatus(code=HttpStatus.OK)
	public UserDto doLogin(@Valid @RequestBody LoginDto loginDto) throws LoginFailException, MapperConvertDtoException {
		UserDto userDto = new UserDto();
		List<User> users = userRepository.findByLogin(loginDto.getLogin());
		if(!users.isEmpty()) {
			User user = users.get(0);
			if(user.getPassword().equals(loginDto.getPassword())) {
				userDto = (UserDto) ModelMapperHelper.getInstance().convert(User.class, user);
			} else {
				throw new LoginFailException();
			}
		} else {
			throw new LoginFailException();
		}	
		
		return userDto;
		
	}
	
	@PostMapping(path = "/create", consumes="application/json", produces= {"application/json"})
	@ResponseStatus(code=HttpStatus.CREATED)
	public UserDto createUser(@RequestBody UserDto userDto) throws MapperConvertDtoException, LoginConflictException {
		
		String login = userDto.getLogin();
		
		if(userRepository.findByLogin(login).isEmpty()) {
			User user = (User) ModelMapperHelper.getInstance().convert(User.class, userDto);
			user.setRegistrationDate(LocalDateTime.now());
			user.setType(UserType.CLIENT);
			userRepository.save(user);			
			userDto = (UserDto) ModelMapperHelper.getInstance().convert(User.class, user);
			
		} else {
			throw new LoginConflictException();
		}
		
		return userDto;
	}
	
	@PostMapping(path = "/update", consumes="application/json", produces= {"application/json"})
	@ResponseStatus(code=HttpStatus.OK)
	public void updateUser(@Valid @RequestBody UserDto userDto) throws MapperConvertDtoException, EntityNotFoundException {
		
		User user = (User) ModelMapperHelper.getInstance().convert(User.class, userDto);
		Optional<User> optUserDB = userRepository.findById(user.getId());
		if(optUserDB.isPresent()) {
			User userDB = optUserDB.get();
			userDB.setFirstName(user.getFirstName());
			userDB.setLastName(user.getLastName());
			userDB.setPhone(user.getPhone());
			userDB.setAddress(user.getAddress());
			
			userRepository.save(userDB);
		} else {
			throw new EntityNotFoundException();
		}
		
	}
	
	@DeleteMapping("/{idUser}")
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	public void removerTarefa(@PathVariable Long idUser) {
		userRepository.deleteById(idUser);
	}
	
	
	@ExceptionHandler(Exception.class)
	public void handleExcetion(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
	}
	
	@ExceptionHandler(MapperConvertDtoException.class)
	public void handleMapperConvertDtoException(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "parse error");
	}
	
	@ExceptionHandler(LoginConflictException.class)
	public void handleLoginConflictException(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "conflict");
	}
	
	@ExceptionHandler(LoginFailException.class)
	public void handleLoginFailException(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "login fail");
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	public void handleEntityNotFoundException(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.NO_CONTENT.value(), "entity no found");
	}
}
