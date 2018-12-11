package com.base.engine;

public class BasicShader extends Shader{
	
private static final BasicShader instance = new BasicShader();
 	
 	public static BasicShader getInstance()
 	{
 		return instance;
 	}
 	
 	private BasicShader()
 	{
 		super();
 		
 		
		addVertexShaderFromFile("basicVertex5.vs");
 		addFragmentShaderFromFile("basicFragment5.fs");
 		compileShader();
 		
 		addUniform("transform");
 		addUniform("color");
 	}
 	
 	public void updateUniforms(Matrix4f worldMatrix, Matrix4f projectedMatrix, Material material)
 	{
 		if(material.getTexture() != null)
 			material.getTexture().bind();
 		else
 			RenderUtil.unbindTextures();
 		
 		setUniform("transform", projectedMatrix);
 		setUniform("color", material.getColor());
 	}

}
