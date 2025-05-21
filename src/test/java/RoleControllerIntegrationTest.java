// // File: RoleControllerIntegrationTest.java
// import static org.hamcrest.Matchers.*;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.project.app.domain.dto.RoleDTO;
// import com.project.app.domain.ports.RoleUsecase;

// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.http.MediaType;
// import org.springframework.test.web.servlet.MockMvc;

// @SpringBootTest
// @AutoConfigureMockMvc
// public class RoleControllerIntegrationTest {

//     @Autowired
//     private MockMvc mockMvc;

//     @Autowired
//     private ObjectMapper objectMapper;

//     @Autowired
//     private RoleUsecase roleUsecase;

//     @Test
//     public void testCreateRole() throws Exception {
//         RoleDTO role = new RoleDTO(null, "ADMIN", null, null, null);

//         mockMvc.perform(post("/api/roles")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(objectMapper.writeValueAsString(role)))
//             .andExpect(status().isOk())
//             .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//             .andExpect(jsonPath("$.name", is("ADMIN")));
//     }

//     @Test
//     public void testGetRoleById() throws Exception {
//         RoleDTO role = new RoleDTO(null, "USER", null, null, null);

       

//         RoleDTO created = roleUsecase.create(role);

//         mockMvc.perform(get("/api/roles/{id}", created.getId()))
//             .andExpect(status().isOk())
//             .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//             .andExpect(jsonPath("$.name", is("USER")))
//             .andExpect(jsonPath("$.description", is("User role")));
//     }

//     @Test
//     public void testDeleteRole() throws Exception {
//         RoleDTO role = new RoleDTO(null, "ADMIN", null, null, null);
//         RoleDTO created = roleUsecase.create(role);

//         mockMvc.perform(delete("/api/roles/{id}", created.getId()))
//             .andExpect(status().isNoContent());

//         mockMvc.perform(get("/api/roles/{id}", created.getId()))
//             .andExpect(status().isNotFound());
//     }
// }
