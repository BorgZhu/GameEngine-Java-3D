package com.base.engine;

public class PhongShader extends Shader{
//设置点光源最多的数量	
private static final int MAX_POINT_LIGHTS = 4;
//设置聚光最多的数量

private static final int MAX_SPOT_LIGHTS = 4;

private static final PhongShader instance = new PhongShader();
 	
 	public static PhongShader getInstance()
 	{
 		return instance;
 	}
 	
 	private static Vector3f ambientLight = new Vector3f(0.1f,0.1f,0.1f);
 	private static DirectionalLight directionalLight = new DirectionalLight(new BaseLight(new Vector3f(0,0,0), 0), new Vector3f(0,0,0));
 	
 	private static PointLight[] pointLights = new PointLight[] {};
 	private static SpotLight[] spotLights = new SpotLight[] {};
 	
 	
 	private PhongShader()
 	{
 		super();
 		
 		addVertexShader(ResourceLoader.loadShader("phongVertex4.vs"));
 		addFragmentShader(ResourceLoader.loadShader("phongFragment4.fs"));
 		compileShader();
 		
 		addUniform("transform");
 		addUniform("transformProjected");
 		addUniform("baseColor");
 		addUniform("ambientLight");
 		
		addUniform("specularIntensity");
 		addUniform("specularPower");
 		addUniform("eyePos");

 		
 		
		addUniform("directionalLight.base.color");
 		addUniform("directionalLight.base.intensity");
 		addUniform("directionalLight.direction");
 	
		
 		for(int i = 0; i < MAX_POINT_LIGHTS; i++)
 		{
 			addUniform("pointLights[" + i + "].base.color");
 			addUniform("pointLights[" + i + "].base.intensity");
 			addUniform("pointLights[" + i + "].attenuation.constant");
 			addUniform("pointLights[" + i + "].attenuation.linear");
 			addUniform("pointLights[" + i + "].attenuation.exponent");
 			addUniform("pointLights[" + i + "].position");
 			addUniform("pointLights[" + i + "].range");
 		}
 	
		for(int i = 0; i < MAX_SPOT_LIGHTS; i++)
 		{
 			addUniform("spotLights[" + i + "].pointLight.base.color");
 			addUniform("spotLights[" + i + "].pointLight.base.intensity");
 			addUniform("spotLights[" + i + "].pointLight.attenuation.constant");
 			addUniform("spotLights[" + i + "].pointLight.attenuation.linear");
 			addUniform("spotLights[" + i + "].pointLight.attenuation.exponent");
 			addUniform("spotLights[" + i + "].pointLight.position");
 			addUniform("spotLights[" + i + "].pointLight.range");
 			addUniform("spotLights[" + i + "].direction");
 			addUniform("spotLights[" + i + "].cutoff");
 		}
 	
 	
 	}
 	
 	public void updateUniforms(Matrix4f worldMatrix, Matrix4f projectedMatrix, Material material)
 	{
 		if(material.getTexture() != null)
 			material.getTexture().bind();
 		else
 			RenderUtil.unbindTextures();
 		
 		setUniform("transformProjected", projectedMatrix);
 		setUniform("transform", worldMatrix);
 		setUniform("baseColor", material.getColor());
 		
 		setUniform("ambientLight", ambientLight);
 		setUniform("directionalLight", directionalLight);
 	
 		
		for(int i = 0; i < pointLights.length; i++)
 			setUniform("pointLights[" + i + "]", pointLights[i]);
 	
		for(int i = 0; i < spotLights.length; i++)
 			setUniform("spotLights[" + i + "]", spotLights[i]);
 		
 		
		setUniformf("specularIntensity", material.getSpecularIntensity());
 		setUniformf("specularPower", material.getSpecularPower());
 		
 		setUniform("eyePos", Transform.getCamera().getPos());
 		
 	}
 	
 	public static Vector3f getAmbientLight()
 	{
 		return ambientLight;
 	}

 	public static void setAmbientLight(Vector3f ambientLight)
 	{
 		PhongShader.ambientLight = ambientLight;
 	}
 	
 	public static void setDirectionalLight(DirectionalLight directionalLight)
 	{
 		PhongShader.directionalLight = directionalLight;
 	}
 	
	public static void setPointLight(PointLight[] pointLights)
 	{
 		if(pointLights.length > MAX_POINT_LIGHTS)
 		{
 			System.err.println("Error: You passed in too many point lights. Max allowed is " + MAX_POINT_LIGHTS + ", you passed in " + pointLights.length);
 			new Exception().printStackTrace();
 			System.exit(1);
 		}
 		
 		PhongShader.pointLights = pointLights;
 	}

	public static void setSpotLights(SpotLight[] spotLights)
 	{
 		if(spotLights.length > MAX_SPOT_LIGHTS)
 		{
 			System.err.println("Error: You passed in too many spot lights. Max allowed is " + MAX_SPOT_LIGHTS + ", you passed in " + spotLights.length);
 			new Exception().printStackTrace();
 			System.exit(1);
 		}
 		
 		PhongShader.spotLights = spotLights;
 	}
 	
 	public void setUniform(String uniformName, BaseLight baseLight)
 	{
 		setUniform(uniformName + ".color", baseLight.getColor());
 		setUniformf(uniformName + ".intensity", baseLight.getIntensity());
 	}
 	
 	public void setUniform(String uniformName, DirectionalLight directionalLight)
 	{
 		setUniform(uniformName + ".base", directionalLight.getBase());
 		setUniform(uniformName + ".direction", directionalLight.getDirection());
 	}
 	
 	
	public void setUniform(String uniformName, PointLight pointLight)
 	{
 		setUniform(uniformName + ".base", pointLight.getBaseLight());
 		setUniformf(uniformName + ".attenuation.constant", pointLight.getAttenuation().getConstant());
 		setUniformf(uniformName + ".attenuation.linear", pointLight.getAttenuation().getLinear());
 		setUniformf(uniformName + ".attenuation.exponent", pointLight.getAttenuation().getExponent());
 		setUniform(uniformName + ".position", pointLight.getPosition());
 		setUniformf(uniformName + ".range", pointLight.getRange());
 	}
	
	public void setUniform(String uniformName, SpotLight spotLight)
 	{
 		setUniform(uniformName + ".pointLight", spotLight.getPointLight());
 		setUniform(uniformName + ".direction", spotLight.getDirection());
 		setUniformf(uniformName + ".cutoff", spotLight.getCutoff());
 	}
}
