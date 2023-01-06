package com.cgvsu;

import com.cgvsu.rasterization.MyPoint3D;
import com.cgvsu.render_engine.Camera;
import javafx.collections.ObservableList;

import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

public class Utils {

    public static Vector3d getNormal(MyPoint3D p1, MyPoint3D p2, MyPoint3D p3) {
        Vector3d p1p2 = new Vector3d(p2.getX() - p1.getX(), p2.getY() - p1.getY(), p2.getZ() - p1.getZ());
        Vector3d p1p3 = new Vector3d(p3.getX() - p1.getX(), p3.getY() - p1.getY(), p3.getZ() - p1.getZ());
        double A = p1p2.y * p1p3.z - p1p2.z * p1p3.y;
        double B = -(p1p2.x * p1p3.z - p1p2.z * p1p3.x);
        double C = p1p2.x * p1p3.y - p1p2.y * p1p3.x;
        return new Vector3d(A, B, C);
    }

    public static double getZ(MyPoint3D p1, MyPoint3D p2, MyPoint3D p3, double x, double y) {
        Vector3d normal = getNormal(p1, p2, p3);
        return (-normal.x * (x - p1.getX()) - normal.y * (y - p1.getY())) / normal.z + p1.getZ();
    }

    public static double getCosLight(Camera camera, MyPoint3D p1, MyPoint3D p2, MyPoint3D p3) {
        Vector3d normal = getNormal(p1, p2, p3);
        return Math.abs(
                (camera.getPosition().x * normal.x + camera.getPosition().y * normal.y + camera.getPosition().z * normal.z) /
                        ((Math.sqrt(normal.x * normal.x + normal.y * normal.y + normal.z * normal.z)) *
                                (Math.sqrt(camera.getPosition().x * camera.getPosition().x
                                        + camera.getPosition().y * camera.getPosition().y
                                        + camera.getPosition().z * camera.getPosition().z))));
    }


    public static String Vector3ftoString(Vector3f vector3f) {
        return "x:" + String.format(String.valueOf(vector3f.x), 3) + " y:" +
                String.format(String.valueOf(vector3f.y), 3) + " z:" + String.format(String.valueOf(vector3f.z), 3);
    }

    public static void recalculateIndexes(ObservableList<Camera> cameras, ObservableList<String> listOfCameras) {
        for (int i = 0; i < cameras.size(); i++) {
            listOfCameras.add(i + Utils.Vector3ftoString(cameras.get(i).getPosition()));
            listOfCameras.remove(0);
        }
    }
}
