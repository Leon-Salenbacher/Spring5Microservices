package com.security.jwt.application.spring5microservices.service;

import com.security.jwt.application.spring5microservices.model.User;
import com.security.jwt.application.spring5microservices.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = UserService.class)
public class UserServiceTest {

    @MockBean
    private UserRepository mockUserRepository;

    @MockBean
    private PasswordEncoder mockPasswordEncoder;

    @Autowired
    private UserService userService;


    static Stream<Arguments> loadUserByUsernameTestCases() {
        User inactiveUser = User.builder().username("inactiveUser").active(false).build();
        User activeUser = User.builder().username("activeUser").active(true).build();
        return Stream.of(
                //@formatter:off
                //            username,                     repositoryResult,   expectedException,                 expectedResult
                Arguments.of( null,                         empty(),            UsernameNotFoundException.class,   null ),
                Arguments.of( "NotFound",                   empty(),            UsernameNotFoundException.class,   null ),
                Arguments.of( inactiveUser.getUsername(),   of(inactiveUser),   LockedException.class,             null ),
                Arguments.of( activeUser.getUsername(),     of(activeUser),     null,                              activeUser )
        ); //@formatter:on
    }

    @ParameterizedTest
    @MethodSource("loadUserByUsernameTestCases")
    @DisplayName("loadUserByUsername: test cases")
    public void loadUserByUsername_testCases(String username,
                                             Optional<User> repositoryResult,
                                             Class<? extends Exception> expectedException,
                                             UserDetails expectedResult) {
        when(mockUserRepository.findByUsername(username)).thenReturn(repositoryResult);
        if (null != expectedException) {
            assertThrows(expectedException, () -> userService.loadUserByUsername(username));
        }
        else {
            assertEquals(expectedResult, userService.loadUserByUsername(username));
        }
    }


    static Stream<Arguments> passwordsMatchTestCases() {
        return Stream.of(
                //@formatter:off
                //            rawPassword,     encodedPassword,     passwordEncoderResult,   expectedResult
                Arguments.of( null,            null,                false,                   false ),
                Arguments.of( "",              null,                false,                   false ),
                Arguments.of( "",              "",                  false,                   false ),
                Arguments.of( "rawPassword",   "encodedPassword",   false,                   false ),
                Arguments.of( "rawPassword",   "encodedPassword",   true,                    true )
        ); //@formatter:on
    }

    @ParameterizedTest
    @MethodSource("passwordsMatchTestCases")
    @DisplayName("passwordsMatch: test cases")
    public void passwordsMatch_testCases(String rawPassword,
                                         String encodedPassword,
                                         boolean passwordEncoderResult,
                                         boolean expectedResult) {
        User user = User.builder().username("username").active(true).password(encodedPassword).build();
        when(mockPasswordEncoder.matches(rawPassword, encodedPassword)).thenReturn(passwordEncoderResult);
        assertEquals(expectedResult, userService.passwordsMatch(rawPassword, user));
    }

}
