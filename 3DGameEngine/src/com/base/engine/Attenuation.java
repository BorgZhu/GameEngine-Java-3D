package com.base.engine;

public class Attenuation {
	// 三个衰减参数 -->用于计算点光源 
//	- 分母上添加了三个衰减参数：一个常量参数、一个线性参数和一个指数参数 
//	- 当常量参数设为1，此时就算距离很小，也不会产生无限大的光强，起到滤波的作用，过滤掉不正常数据 
//	- 线性参数用来控制缓慢的衰减效果 
//	- 指数因子可以控制迅速的衰减 效果
	
	private float constant;//常量
 	private float linear;//线性
 	private float exponent;//平方项
	public Attenuation(float constant, float linear, float exponent) {
		this.constant = constant;
		this.linear = linear;
		this.exponent = exponent;
	}
	public float getConstant() {
		return constant;
	}
	public void setConstant(float constant) {
		this.constant = constant;
	}
	public float getLinear() {
		return linear;
	}
	public void setLinear(float linear) {
		this.linear = linear;
	}
	public float getExponent() {
		return exponent;
	}
	public void setExponent(float exponent) {
		this.exponent = exponent;
	}
 	
 	

}
