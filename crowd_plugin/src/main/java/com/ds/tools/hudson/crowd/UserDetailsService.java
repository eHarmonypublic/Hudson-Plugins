package com.ds.tools.hudson.crowd;

import hudson.security.SecurityRealm;

import java.rmi.RemoteException;
import java.util.Arrays;

import org.acegisecurity.GrantedAuthority;

import com.atlassian.crowd.integration.acegi.user.CrowdUserDetailsServiceImpl;
import com.atlassian.crowd.integration.exception.InvalidAuthorizationTokenException;
import com.atlassian.crowd.integration.exception.ObjectNotFoundException;

/**
 * Retrieves users from Crowd using Crowd's remote API.
 * <p>
 * Adds {@link SecurityRealm.AUTHENTICATED_AUTHORITY} to the users' group
 * memberships if they don't already have it.
 * 
 * @author Kutsal Berberoglu
 */
public class UserDetailsService extends CrowdUserDetailsServiceImpl {

	/**
	 * Requests the group memberships of the user from the Crowd Server. Adds
	 * {@link SecurityRealm.AUTHENTICATED_AUTHORITY} to the list if the user
	 * doesn't already have it.
	 */
	@Override
	protected GrantedAuthority[] getAuthorities(String username)
			throws InvalidAuthorizationTokenException, RemoteException,
			ObjectNotFoundException {
		GrantedAuthority[] authorities = super.getAuthorities(username);
		if (authorities != null) {
			int alen = authorities.length;
			String auth = SecurityRealm.AUTHENTICATED_AUTHORITY.getAuthority();
			for (int i = 0; i < alen; i++) {
				if (authorities[i].getAuthority().equalsIgnoreCase(auth)) {
					return authorities;
				}
			}
			authorities = Arrays.copyOf(authorities, alen + 1);
			authorities[alen] = SecurityRealm.AUTHENTICATED_AUTHORITY;
		}
		return authorities;
	}

}
