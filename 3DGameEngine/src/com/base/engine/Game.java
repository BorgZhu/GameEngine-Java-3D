package com.base.engine;

import org.lwjgl.input.Keyboard;

public class Game {

	private Mesh mesh;
	private Shader shader;
	private Transform transform;
	private Camera camera;

	public Game() {
		mesh = ResourceLoader.loadMesh("monkey.obj");
		shader = new Shader();
		camera = new Camera();
//		// ���� ��mesh
//		Vertex[] vertices = new Vertex[] { new Vertex(new Vector3f(-1, -1, 0)), new Vertex(new Vector3f(0, 1, 0)),
//				new Vertex(new Vector3f(1, -1, 0)), new Vertex(new Vector3f(0, -1, 1)) };
//
//		int[] indices = new int[] { 0, 1, 3,
//									3, 1, 2, 
//									2, 1, 0,
//									0, 2, 3 };
//
//		mesh.addVertices(vertices, indices);
		
		Transform.setProjection(70f, Window.getWidth(), Window.getHeight(), 0.1f, 1000);
		Transform.setCamera(camera);
		transform = new Transform();

		// ��ɫ
		// shader.addVertexShader(ResourceLoader.loadShader("basicVertex.vs")); //��
		shader.addVertexShader(ResourceLoader.loadShader("basicVertex1.vs"));
		shader.addFragmentShader(ResourceLoader.loadShader("basicFragment.fs"));
		shader.compileShader();

		// shader.addUniform("uniformFloat"); // -->basicVertex.vs
		shader.addUniform("transform");
	}

	public void input() {
		
		camera.input();
//		if (Input.getKeyDown(Input.KEY_UP))
//			System.out.println("We've just pressed up!");
//		if (Input.getKeyUp(Input.KEY_UP))
//			System.out.println("We've just released up!");
//
//		if (Input.getMouseDown(1))
//			System.out.println("We've just right clicked at " + Input.getMousePosition().toString());
//		if (Input.getMouseUp(1))
//			System.out.println("We've just released right mouse button!");
	}

	float temp = 0.0f;

	public void update() {
		temp += Time.getDelta();

		// shader.setUniformf("uniformFloat", (float)Math.abs(Math.sin(temp)));
		// //--->basicVertex.vs
		float sinTemp = (float) Math.sin(temp);

		transform.setTranslation(sinTemp, 0, 5);
		transform.setRotation(0,sinTemp * 180, 0);
//		transform.setScale(0.7f*sinTemp, 0.7f*sinTemp, 0.7f*sinTemp);

	}

	public void render() {
		shader.bind();
		shader.setUniform("transform", transform.getProjectedTransformation());
		mesh.draw();
	}
}
