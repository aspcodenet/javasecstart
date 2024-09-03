package se.systementor.javasecstart.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDetailsService2 {

    UserDetails loadUserByEmail(String email) throws UsernameNotFoundException;

}
