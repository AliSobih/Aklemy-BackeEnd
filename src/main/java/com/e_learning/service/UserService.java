package com.e_learning.service;

import com.e_learning.dao.PasswordResetTokenDAO;
import com.e_learning.dao.RoleDao;
import com.e_learning.dao.UserDao;
import com.e_learning.dto.RegisterDTO;
import com.e_learning.dto.ResetPasswordDTO;
import com.e_learning.dto.UserDTO;
import com.e_learning.dto.customer.ChangePasswordDTO;
import com.e_learning.entities.PasswordResetToken;
import com.e_learning.entities.Role;
import com.e_learning.entities.User;
import com.e_learning.entities.mapper.UserMapper;
import com.e_learning.security.InvalidUserNameOrPasswordException;
import com.e_learning.security.config.congirmation.ConfirmationToken;
import com.e_learning.security.config.congirmation.ConfirmationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService extends BaseServiceImp<User> implements UserDetailsService {
    @Value("${baseUrl}")
    private String base;
    private final static String USER_NOT_FOUND_MSG =
            "user with email %s not found";
    @Autowired
    private ConfirmationTokenService confirmationTokenService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordResetTokenDAO passwordResetTokenDAO;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public JpaRepository<User, Long> Repository() {
        return userDao;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return userDao.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                String.format(USER_NOT_FOUND_MSG, email)));
    }

    @Transactional
    public String signUpUser(RegisterDTO registerDTO) {
        boolean userExists = userDao
                .findByEmail(registerDTO.getEmail())
                .isPresent();

        if (userExists) {
            return null;
//            throw new IllegalStateException("email already taken");
        }

        String encodedPassword = bCryptPasswordEncoder
                .encode(registerDTO.getPassword());

        registerDTO.setPassword(encodedPassword);
        User user = new User();
        user.setEmail(registerDTO.getEmail());
        user.setPassword(encodedPassword);
        user.setName(registerDTO.getName());
        user.setNameAr(registerDTO.getNameAr());
        Role role = null;
        if (!roleDao.existsByName("ROLE_STUDENT")) {
            role = new Role("ROLE_STUDENT", "طالب");
            roleDao.save(role);
        } else {
            role = roleDao.findByName("ROLE_STUDENT");
        }
        user.setRole(role);
        userDao.save(user);

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );

        confirmationTokenService.saveConfirmationToken(
                confirmationToken);
        return token;
    }

    public int enableAppUser(String email) {
        return userDao.enableUser(email);
    }

    public  UserDTO findByEmail(String email){
        Optional<User> user = this.userDao.findByEmail(email);
        UserDTO userDTO = null;
        if(user.isPresent()){
            userDTO = this.userMapper.toDTO(user.get());
        }
        return userDTO;
    }

    public boolean changePassword(ChangePasswordDTO changePasswordDTO , Long id ){
        Optional<User> optionalUser  = userDao.findById(id);
        if(optionalUser.isPresent()){
            boolean flag  =
                    bCryptPasswordEncoder.matches(changePasswordDTO.getOldPassword(), optionalUser.get().getPassword());
            if(flag){
                String newEncryptedPassword = bCryptPasswordEncoder.encode(changePasswordDTO.getNewPassword());
                userDao.changePassword(newEncryptedPassword, id);
                return true ;
            }
            else{
                throw new InvalidUserNameOrPasswordException("oldPass","wrong password");
            }
        }
        return  false;
    }

    public boolean createPasswordResetToken(String email) {
        Optional<User> customer = userDao.findByEmail(email);
        if(customer.isPresent()) {
            PasswordResetToken token = new PasswordResetToken();
            token.setToken(UUID.randomUUID().toString());
            token.setEmail(email);
            token.setExpirationTime(LocalDateTime.now().plusMinutes(15));
            passwordResetTokenDAO.save(token);
            String url = base + "/reset-password?token=" + token.getToken();
            String subject = "Password Reset Request";
            String message = "Click the link to reset your password: " + url;

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(email);
            mailMessage.setSubject(subject);
            mailMessage.setText(message);

            mailSender.send(mailMessage);
            return true;
        }
        return false;
    }

    public boolean conformResetPassword(String token) {
        PasswordResetToken passwordResetToken = passwordResetTokenDAO.findByToken(token);
        return passwordResetToken != null && passwordResetToken.getExpirationTime().isAfter(LocalDateTime.now());
    }

    @Transactional
    public boolean resetPassword(ResetPasswordDTO resetPasswordDTO) {
        PasswordResetToken passwordResetToken = passwordResetTokenDAO.findByToken(resetPasswordDTO.getToken());
        if (passwordResetToken != null && passwordResetToken.getExpirationTime().isAfter(LocalDateTime.now())) {
            if (passwordResetToken.getEmail() != null) {
                passwordResetTokenDAO.deleteByEmail(passwordResetToken.getEmail());
            }
            Optional<User> user = userDao.findByEmail(passwordResetToken.getEmail());
            if (user.isPresent()) {
                String password = passwordEncoder.encode(resetPasswordDTO.getNewPassword());
                user.get().setPassword(password);
                return true;
            }
        }
        return false;
    }

    public Long getRole(long customerId){
        Optional<User> user =this.userDao.findById(customerId);
        return user.map(value -> value.getRole().getId()).orElse(null);
    }
}
