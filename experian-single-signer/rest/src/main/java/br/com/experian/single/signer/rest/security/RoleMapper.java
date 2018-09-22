package br.com.experian.single.signer.rest.security;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RoleMapper {
	
	private String rolePrefix = "ROLE_";
	
	//Maps roles to transactions
	private HashMap<String, List<String>> roleMapping;
	
	private Set<String> uniqueTransactions;
	
	public RoleMapper(HashMap<String, List<String>> roleMapping){
		this.roleMapping = roleMapping;
		
		uniqueTransactions = new HashSet<String>();
		
		for (List<String> transactions : roleMapping.values()) {
			uniqueTransactions.addAll(transactions);
		}
	}

	public Set<String> getUniqueTransactions() {
		return uniqueTransactions;
	}

	public HashMap<String, List<String>> getRoleMapping() {
		return roleMapping;
	}
	
	public void setRolePrefix(String rolePrefix) {
		this.rolePrefix = rolePrefix;
	}

	public String getRolePrefix() {
		return rolePrefix;
	}

}
