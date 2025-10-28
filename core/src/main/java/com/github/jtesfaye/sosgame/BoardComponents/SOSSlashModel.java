package com.github.jtesfaye.sosgame.BoardComponents;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public class SOSSlashModel {

    private static final ModelBuilder builder = new ModelBuilder();

    public static ModelInstance SOSSlashInstance(Vector3 start, Vector3 middle, Vector3 end, Color color) {

        float length = start.dst(end);
        length += start.dst(middle);
        Vector3 dir = new Vector3(end).sub(start).nor();
        middle.y += .05;
        Model model = builder.createCylinder(
            0.1f, length, 0.1f, 10,
            new Material(ColorAttribute.createDiffuse(color)),
            VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal
        );

        ModelInstance instance = new ModelInstance(model);

        Vector3 up = new Vector3(0, 1, 0);
        Quaternion rotation = new Quaternion();
        rotation.setFromCross(up, dir);

        instance.transform.set(rotation);
        instance.transform.setTranslation(middle);



        return instance;
    }
}
