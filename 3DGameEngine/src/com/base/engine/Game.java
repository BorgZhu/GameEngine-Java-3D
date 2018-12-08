package com.base.engine;

import org.lwjgl.input.Keyboard;

public class Game {

	private Mesh mesh;
	private Shader shader;
	private Transform transform;
	
	public Game() {
		mesh = new Mesh();
		shader = new Shader();
		
		//赋点 做mesh
		Vertex[] data = new Vertex[] { new Vertex(new Vector3f(-1, -1, 0)), new Vertex(new Vector3f(0, 1, 0)),
				new Vertex(new Vector3f(1, -1, 0)) };

		mesh.addVertices(data);
		
		transform = new Transform();
		
		//着色
		//shader.addVertexShader(ResourceLoader.loadShader("basicVertex.vs"));  //改
		shader.addVertexShader(ResourceLoader.loadShader("basicVertex1.vs"));
 		shader.addFragmentShader(ResourceLoader.loadShader("basicFragment.fs"));
 		shader.compileShader();
		
 		//shader.addUniform("uniformFloat");  // -->basicVertex.vs
 		shader.addUniform("transform");
	}

	public void input() {
		if (Input.getKeyDown(Input.KEY_UP))
			System.out.println("We've just pressed up!");
		if (Input.getKeyUp(Input.KEY_UP))
			System.out.println("We've just released up!");

		if (Input.getMouseDown(1))
			System.out.println("We've just right clicked at " + Input.getMousePosition().toString());
		if (Input.getMouseUp(1))
			System.out.println("We've just released right mouse button!");
	}
	float temp = 0.0f;
 	

	public void update() {
		temp += Time.getDelta();
 		
 		//shader.setUniformf("uniformFloat", (float)Math.abs(Math.sin(temp)));  //--->basicVertex.vs
		float sinTemp = (float)Math.sin(temp);
 		
 		transform.setTranslation(sinTemp, 0, 0);
 		transform.setRotation(0, 0, sinTemp * 180);
 		transform.setScale(sinTemp, sinTemp, sinTemp);
	
	}

	public void render() {
		shader.bind();
		shader.setUniform("transform", transform.getTransformation());
		mesh.draw();
	}
}
