package com.base.engine;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

public class Mesh {
	private int vbo;
	private int ibo;
 	private int size;
 	
 	public Mesh()
 	{
 		vbo = glGenBuffers();
 		ibo = glGenBuffers();
 		size = 0;
 	}
 	
 	public void addVertices(Vertex[] vertices, int[] indices)
 	{
 		addVertices(vertices, indices, false);
 	}
 	//������������ �Ƿ���㶥�㷨���� ���㷨�����������ڼ����������ֵ
 	public void addVertices(Vertex[] vertexs,int[] indices,boolean calcNormals)
 	{	
 		
 		if(calcNormals)
 		{
 			calcNormals(vertexs, indices);
 		}
 		size = indices.length;
 		
 		glBindBuffer(GL_ARRAY_BUFFER, vbo);
 		glBufferData(GL_ARRAY_BUFFER, Util.createFlippedBuffer(vertexs), GL_STATIC_DRAW);
 	
 		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
 		glBufferData(GL_ELEMENT_ARRAY_BUFFER, Util.createFlippedBuffer(indices), GL_STATIC_DRAW);
 	
 	}
 	
 	public void draw() 
 	{	
 		//����� 0 1 2 ��.vs�ļ���layerһһ��Ӧ
 		glEnableVertexAttribArray(0);
 		glEnableVertexAttribArray(1);
 		glEnableVertexAttribArray(2);
 		
 		glBindBuffer(GL_ARRAY_BUFFER, vbo);
 		glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.SIZE * 4, 0); // 3άpos  0��4*3  --�� 0-12
 		glVertexAttribPointer(1, 2, GL_FLOAT, false, Vertex.SIZE * 4, 12);//2άtexCoord 12��2*8+12 ---�� 12-20
 		glVertexAttribPointer(2, 3, GL_FLOAT, false, Vertex.SIZE * 4, 20);//3ά normal 20-32
 		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		glDrawElements(GL_TRIANGLES, size,GL_UNSIGNED_INT,0);
 		
 		glDisableVertexAttribArray(0);
 		glDisableVertexAttribArray(1);
 		glDisableVertexAttribArray(2);
 		
 	}
 	
 	
 	//���㶥��ķ��߼���  
	private void calcNormals(Vertex[] vertices, int[] indices)
 	{
 		for(int i = 0; i < indices.length; i += 3)
 		{
 			int i0 = indices[i];
 			int i1 = indices[i + 1];
 			int i2 = indices[i + 2];
 			//�����η��� ������������ߵ�����ֵ
 			Vector3f v1 = vertices[i1].getPos().sub(vertices[i0].getPos());
 			//�����η��� ������������ߵ�����ֵ
 			Vector3f v2 = vertices[i2].getPos().sub(vertices[i0].getPos());
 			
 			//���� ������������� �õ���mesh�ķ�����
 			Vector3f normal = v1.cross(v2).normalized();
 			
 			vertices[i0].setNormal(vertices[i0].getNormal().add(normal));
 			vertices[i1].setNormal(vertices[i1].getNormal().add(normal));
 			vertices[i2].setNormal(vertices[i2].getNormal().add(normal));
 		}
 		
 		for(int i = 0; i < vertices.length; i++)
 			vertices[i].setNormal(vertices[i].getNormal().normalized());
 	}
	
}
