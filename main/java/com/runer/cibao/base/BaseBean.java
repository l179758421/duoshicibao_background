package com.runer.cibao.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
public class BaseBean implements Serializable {





}
