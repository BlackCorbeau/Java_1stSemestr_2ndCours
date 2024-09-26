package org.example;

public class Funks {
    public static class Point {
        double x;
        double y;

        Point(double _x, double _y) {
            this.x = _x;
            this.y = _y;
        }
    }

    public static boolean onSegment(Point p, Point q, Point r) {
        if (q.x <= Math.max(p.x, r.x) && q.x >= Math.min(p.x, r.x) &&
                q.y <= Math.max(p.y, r.y) && q.y >= Math.min(p.y, r.y))
            return true;
        return false;
    }

    public static int orientation(Point p, Point q, Point r){
        double val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);
        if (val == 0) return 0;  // коллинеарные
        return (val > 0) ? 1 : 2; // по часовой или против часовой
    }

    // Функция для преобразования числа на циферблате в координаты точки на окружности
    public static Point numberToPoint(int number) {
        double angle = Math.toRadians(30 * (12 - number)); // Угол в радианах
        double x = Math.cos(angle);
        double y = Math.sin(angle);
        return new Point(x, y);
    }

    public boolean doIntersect(Point p1, Point q1, Point p2, Point q2) {

        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);

        // Общий случай
        if (o1 != o2 && o3 != o4) return true;

        // Особые случаи
        // p1, q1 и p2 коллинеарны и p2 лежит на отрезке p1q1
        if (o1 == 0 && onSegment(p1, p2, q1)) return true;

        // p1, q1 и q2 коллинеарны и q2 лежит на отрезке p1q1
        if (o2 == 0 && onSegment(p1, q2, q1)) return true;

        // p2, q2 и p1 коллинеарны и p1 лежит на отрезке p2q2
        if (o3 == 0 && onSegment(p2, p1, q2)) return true;

        // p2, q2 и q1 коллинеарны и q1 лежит на отрезке p2q2
        if (o4 == 0 && onSegment(p2, q1, q2)) return true;

        return false; // Не пересекаются
    }
}
