package anUniversitar;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AnUniversitar {
    public List<Saptamana> saptamani = new ArrayList<>();
    public static int[] lista = new int[]{
                    -1,12,13,14,14,14,14,14,14,
                    1,2,3,4,5,6,7,8,8,9,10,11,12,13,14,14,14,14,14,14,
                    0,0,0,0,0,0,0,0,0,0,0,
                    1,2,3,4,5,6,7,8,9,10,11,12,12,12

    };
    public AnUniversitar() {
        for(int i = 0; i<13; i++){
            Saptamana n = new Saptamana(i);
            n.setVacanta(false);
            saptamani.add(n);
        }

        for(int i= 13;i< 15;i++) {
            Saptamana n = new Saptamana(i);
            n.setVacanta(true);

            saptamani.add(n);
        }

        for(int i=15;i<20;i++) {
            Saptamana n = new Saptamana(i);
            n.setVacanta(false);
            saptamani.add(n);
        }
        Saptamana a = new Saptamana(21);
        a.setVacanta(true);
        saptamani.add( new Saptamana(21));

        for(int i=22;i < 31;i++) {
            Saptamana n = new Saptamana(i);
            n.setVacanta(false);
            saptamani.add(n);
        }

        a = new Saptamana(31);
        a.setVacanta(true);
        saptamani.add( a);

        for(int i= 32; i< 41;i++){
            Saptamana n = new Saptamana(i);
            n.setVacanta(false);
            saptamani.add(n);
        }

        for(int i=41;i<54;i++) {
            Saptamana n = new Saptamana(i);
            n.setVacanta(true);
            saptamani.add(n);
        }
    }
    public int getWeek(int id){
        while(true){
            if(!saptamani.get(id).vacanta)
                return id;
            id--;
        }
    }
    public static int getCurrentWeek(){
//        return Integer.parseInt(new SimpleDateFormat("w").format(new java.util.Date()))-40+1;
        int week = Integer.parseInt(new SimpleDateFormat("w").format(new java.util.Date()));
        week = lista[week];
//        System.out.println(lista[42]);
        return week;
    }
}
