package com.healthapp.user.mapper;

import com.healthapp.user.model.dto.RegisterRequest;
import com.healthapp.user.model.dto.UserDto;
import com.healthapp.user.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * MapStruct mapper for User entity and DTOs.
 */
@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper {
    
    /**
     * Maps User entity to UserDto.
     */
    @Mapping(target = "id", expression = "java(user.getId().toString())")
    UserDto toDto(User user);
    
    /**
     * Maps RegisterRequest to User entity.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "displayName", ignore = true)
    @Mapping(target = "avatarUrl", ignore = true)
    @Mapping(target = "dateOfBirth", ignore = true)
    @Mapping(target = "gender", ignore = true)
    @Mapping(target = "emailVerified", ignore = true)
    @Mapping(target = "phoneVerified", ignore = true)
    @Mapping(target = "googleId", ignore = true)
    @Mapping(target = "appleId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "lastLoginAt", ignore = true)
    User toEntity(RegisterRequest request);
    
    /**
     * Updates User entity from UpdateProfileRequest.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "phone", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "emailVerified", ignore = true)
    @Mapping(target = "phoneVerified", ignore = true)
    @Mapping(target = "googleId", ignore = true)
    @Mapping(target = "appleId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "lastLoginAt", ignore = true)
    void updateFromDto(com.healthapp.user.model.dto.UpdateProfileRequest request, @MappingTarget User user);
}
