package com.project.app.domain.ports;



import java.util.List;


import com.project.app.domain.dto.PagedResponse;
import com.project.app.domain.dto.UserDTO;

public interface UserUsecase {

    UserDTO create(UserDTO user);
     UserDTO update(UserDTO user);
     UserDTO patch(UserDTO user);
     UserDTO getById(Long id);
     List<UserDTO> getAll();
     PagedResponse<UserDTO> getAllPaged(int offset, int limit);
     void delete(Long id);

  

  

  

    
}
