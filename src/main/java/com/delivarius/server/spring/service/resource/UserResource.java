package com.delivarius.server.spring.service.resource;

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

import com.delivarius.server.spring.domain.User;
import com.delivarius.server.spring.domain.exception.EntityNotFoundException;
import com.delivarius.server.spring.domain.exception.LoginConflictException;
import com.delivarius.server.spring.domain.exception.LoginFailException;
import com.delivarius.server.spring.repository.UserRepository;
import com.delivarius.server.spring.service.dto.LoginDto;
import com.delivarius.server.spring.service.dto.UserDto;
import com.delivarius.server.spring.service.dto.UserRegisterDto;
import com.delivarius.server.spring.service.dto.mapper.ModelMapperHelper;
import com.delivarius.server.spring.service.dto.mapper.exception.MapperConvertDtoException;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserResource extends AbstractResource{

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping(path="/{idUser}",produces={"application/json"})
	@ResponseStatus(code=HttpStatus.OK)
	public UserDto getUser(@PathVariable Long idUser) throws MapperConvertDtoException, EntityNotFoundException{
		UserDto userDto = new UserDto();
		Optional<User> user = userRepository.findById(idUser);
		if(user.isPresent()) {
			userDto = (UserDto) ModelMapperHelper.getInstance().convert(User.class, user.get());
		} else {
			throw new EntityNotFoundException();
		}		
		return userDto;
	}
	
	@DeleteMapping("/{idUser}")
	@ResponseStatus(code=HttpStatus.OK)
	public void removeUser(@PathVariable Long idUser) throws EntityNotFoundException {
		Optional<User> user =  userRepository.findById(idUser);
		if(user.isPresent()) {
			userRepository.delete(user.get());
		} else {
			throw new EntityNotFoundException();
		}
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
	@ResponseStatus(code=HttpStatus.OK)
	public UserDto createUser(@RequestBody @Valid UserRegisterDto userRegisterDto) throws MapperConvertDtoException, LoginConflictException {
		
		UserDto userDto = userRegisterDto.getUser();
		String login = userDto.getLogin();
		
		boolean empty = userRepository.findByLogin(login).isEmpty();
		if(empty) {
			User user = (User) ModelMapperHelper.getInstance().convert(User.class, userDto);
			user.setPassword(userRegisterDto.getPassword());
			user.setType(userRegisterDto.getType());
			user.setRegistrationDate(LocalDateTime.now());
			userRepository.save(user);			
			userDto = (UserDto) ModelMapperHelper.getInstance().convert(User.class, user);
			
		} else {
			throw new LoginConflictException();
		}
		
		return userDto;
	}
	
	@PostMapping(path = "/update", consumes="application/json", produces= {"application/json"})
	@ResponseStatus(code=HttpStatus.OK)
	public UserDto updateUser(@Valid @RequestBody UserDto userDto) throws MapperConvertDtoException, EntityNotFoundException {
		
		User user = (User) ModelMapperHelper.getInstance().convert(User.class, userDto);
		Optional<User> optUserDB = userRepository.findById(user.getId());
		if(optUserDB.isPresent()) {
			User userDb = optUserDB.get();
			userDb.setName(user.getName());
			userDb.setEmail(user.getEmail());
			userDb.setBirthDate(user.getBirthDate());
			userDb.setPhone(user.getPhone());
			userDb.setAddress(user.getAddress());
			
			userRepository.save(userDb);
			
			userDto = (UserDto) ModelMapperHelper.getInstance().convert(User.class, userDb);
		} else {
			throw new EntityNotFoundException();
		}
		
		return userDto;
		
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
