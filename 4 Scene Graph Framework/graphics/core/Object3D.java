package graphics.core;

import java.util.ArrayList;

public class Object3D
{
	public Matrix transform;
	public Object3D parent;
	public ArrayList<Object3D> children;

	public Object3D()
	{
		transform = Matrix.makeIdentity();
		parent = null;
		children = new ArrayList<Object3D>();
	}

	public void add(Object3D child)
	{
		children.add(child);
		child.parent = this;
	}

	public void remove(Object3D child)
	{
		children.remove(child);
		child.parent = null;
	}

	// calculate transformation of this Object3D relative
	//   to the root Object3D of the scene graph
	public Matrix getWorldMatrix()
	{
		if (parent == null)
			return transform;
		else
			return Matrix.multiply( parent.getWorldMatrix(), transform );
	}

	// return a single list containing all descendants
	public ArrayList<Object3D> getDescendentList()
	{
		ArrayList<Object3D> descendents = new ArrayList<Object3D>();
		// nodes to be added to descendant list,
		//   and whose children will be added to this list
		ArrayList<Object3D> nodesToProcess = new ArrayList<Object3D>();
		nodesToProcess.add(this);

		// continue processing nodes while any are left
		while ( nodesToProcess.size() > 0)
		{
			// remove first node from list
			Object3D node = nodesToProcess.remove(0);
			// add this node to descendant list
			descendents.add(node);
			// children of this node must also be processed
			for (Object3D child : node.children)
				nodesToProcess.add(0, child);
		}
		return descendents;
	}

	// apply geometric transformations
	public void applyMatrix(Matrix m, boolean local)
	{
		if (local)
			transform.rightMultiply(m);
		else
			transform.leftMultiply(m);
	}

	public void translate(float x, float y, float z, boolean local)
	{
		applyMatrix(Matrix.makeTranslation(x,y,z), local);
	}

	public void rotateX(float angle, boolean local)
	{
		applyMatrix(Matrix.makeRotationX(angle), local);
	}

	public void rotateY(float angle, boolean local)
	{
		applyMatrix(Matrix.makeRotationY(angle), local);
	}

	public void rotateZ(float angle, boolean local)
	{
		applyMatrix(Matrix.makeRotationZ(angle), local);
	}

	public void scale(float s, boolean local)
	{
		applyMatrix(Matrix.makeScale(s), local);
	}

	// get/set position components of transform
	public Vector getPosition()
	{
		return new Vector(
			transform.values[0][3],
			transform.values[1][3],
			transform.values[2][3] );
	}

	public Vector getWorldPosition()
	{
		Matrix worldTransform = getWorldMatrix();
		return new Vector(
			worldTransform.values[0][3],
			worldTransform.values[1][3],
			worldTransform.values[2][3] );
	}	

	public void setPosition(Vector position)
	{
		transform.values[0][3] = position.values[0];
		transform.values[1][3] = position.values[1];
		transform.values[2][3] = position.values[2];
	}

}