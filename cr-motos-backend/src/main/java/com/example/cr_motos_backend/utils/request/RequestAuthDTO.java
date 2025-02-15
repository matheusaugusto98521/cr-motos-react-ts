package com.example.cr_motos_backend.utils.request;

public record RequestAuthDTO<T>(T data, String password) {

}
