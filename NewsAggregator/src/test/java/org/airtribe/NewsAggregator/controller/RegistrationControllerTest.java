package org.airtribe.NewsAggregator.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.airtribe.NewsAggregator.entity.User;
import org.airtribe.NewsAggregator.model.UserModel;
import org.airtribe.NewsAggregator.service.impl.JwtService;
import org.airtribe.NewsAggregator.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {RegistrationController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class RegistrationControllerTest {
    @MockBean
    private JwtService jwtService;

    @Autowired
    private RegistrationController registrationController;

    @MockBean
    private UserService userService;

    /**
     * Method under test:
     * {@link RegistrationController#register(UserModel, HttpServletRequest)}
     */
    @Test
    void testRegister() throws Exception {
        // Arrange
        User user = new User();
        user.setEmail("ajay.vala@gmail.com");
        user.setEnabled(true);
        user.setFirstName("Ajay");
        user.setLastName("Vala");
        user.setPassword("test@123");
        user.setRole("User");
        user.setUserId(1L);
        when(userService.registerUser(Mockito.<UserModel>any())).thenReturn(user);
        doNothing().when(userService).createVerificationToken(Mockito.<User>any(), Mockito.<String>any());

        UserModel userModel = new UserModel();
        userModel.setEmail("ajay.vala@gmail.com");
        userModel.setFirstName("Ajay");
        userModel.setLastName("Vala");
        userModel.setPassword("test@123");
        String content = (new ObjectMapper()).writeValueAsString(userModel);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(registrationController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"userId\":1,\"firstName\":\"Ajay\",\"lastName\":\"Vala\",\"email\":\"ajay.vala@gmail.com\",\"password\":\"test@123\""
                                        + ",\"role\":\"User\",\"enabled\":true,\"username\":\"ajay.vala@gmail.com\",\"authorities\":[],\"accountNonExpired\":true,\"accountNonLocked\":true,\"credentialsNonExpired\":true}"));
    }
}
