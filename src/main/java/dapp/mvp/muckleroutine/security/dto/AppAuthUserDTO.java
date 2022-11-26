package dapp.mvp.muckleroutine.security.dto;

import dapp.mvp.muckleroutine.dto.UserDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;

@Log4j2
@Getter
@Setter
@ToString
public class AppAuthUserDTO extends User{
	
	private UserDTO userDTO;
	
	public AppAuthUserDTO(UserDTO userDTO ,String pw, Collection<? extends GrantedAuthority> authorities){
		super(userDTO.getWalletAddress(), pw, authorities);
		this.userDTO = userDTO;
	}
}
