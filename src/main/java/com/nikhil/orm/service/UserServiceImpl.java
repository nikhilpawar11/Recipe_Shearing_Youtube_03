package com.nikhil.orm.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nikhil.orm.Dto.UserDto;
import com.nikhil.orm.entity.User;
import com.nikhil.orm.exception.ResourceNotFoundException;
import com.nikhil.orm.peginationhelper.Helper;
import com.nikhil.orm.peginationhelper.PegiableResponse;
import com.nikhil.orm.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private PasswordEncoder encoder;

	
	@Override
	public UserDto createUser(UserDto userDto) {
		
		User user = mapper.map(userDto, User.class);
		
		user.setId(UUID.randomUUID().toString());
		
		user.setPassword(encoder.encode(userDto.getPassword()));
		
		User saveUser = userRepo.save(user);
		
		return mapper.map(saveUser, UserDto.class) ;
	}

	@Override
	public UserDto updateUser(UserDto userDto, String userId) {
		
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found wth given id "+userId));
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		
		User updateuser = userRepo.save(user);
		
		return mapper.map(updateuser, UserDto.class);
	}

	@Override
	public void deleteUser(String userId) {
		
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found wth given id "+userId));
		
		userRepo.delete(user);
		
	}

	@Override
	public UserDto getUserById(String userId) {
		
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found wth given id "+userId));
		
		return mapper.map(user, UserDto.class);
	}

	@Override
	public List<UserDto> getAllUser() {
		
		List<User> allUser = userRepo.findAll();
		
		List<UserDto> userDto = allUser.stream().map(ex -> mapper.map(ex, UserDto.class)).collect(Collectors.toList());
		
		return userDto;
	}

	@Override
	public PegiableResponse<UserDto> getUserWithPegination(int pageNumber, int pageSize, String sortBy, String sortDir) {
		
		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		
		Page<User> page = userRepo.findAll(pageable);
		
		PegiableResponse<UserDto> peginationResponse = Helper.getPeginationResponse(page, UserDto.class);
		
		return peginationResponse;
	}

}
