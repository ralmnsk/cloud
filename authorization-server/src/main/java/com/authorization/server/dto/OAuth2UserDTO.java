package com.authorization.server.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

public class OAuth2UserDTO implements OAuth2AuthenticatedPrincipal, Serializable {

    private static final long serialVersionUID = -7404669332010983971L;
    private Map<String,Object> attributes;
    private Collection<? extends GrantedAuthority> authorities;
    private String name;

    private  Boolean active;

    private String scope;

    public OAuth2UserDTO(Map<String, Object> attributes, Collection<? extends GrantedAuthority> authorities, String name, Boolean active, String scope) {
        this.attributes = attributes;
        this.authorities = authorities;
        this.name = name;
        this.active = active;
        this.scope = scope;
    }

    @Override
    public <A> A getAttribute(String name) {
        return OAuth2AuthenticatedPrincipal.super.getAttribute(name);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return name;
    }

    public Boolean getActive() {
        return active;
    }

    public String getScope() {
        return scope;
    }
}
