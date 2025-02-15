import java.util.*;

class Mahalle {
    String ad;
    double nufusYogunlugu, ulasimAltyapisi, maliyet, cevreselEtki, sosyalFayda;
    double toplamSkor;

    Mahalle(String ad, double nufusYogunlugu, double ulasimAltyapisi, double maliyet, double cevreselEtki, double sosyalFayda) {
        this.ad = ad;
        this.nufusYogunlugu = nufusYogunlugu;
        this.ulasimAltyapisi = ulasimAltyapisi;
        this.maliyet = maliyet;
        this.cevreselEtki = cevreselEtki;
        this.sosyalFayda = sosyalFayda;
    }
}

public class SoftmaxMahalle {
    public static void main(String[] args) {
        List<Mahalle> mahalleler = Arrays.asList(
                new Mahalle("Karahıdır Mahallesi", 8.0, 5.0, 2.0, 4.0, 7.0),
                new Mahalle("İstasyon Mahallesi", 6.0, 4.5, 3.0, 3.5, 6.5),
                new Mahalle("Pınar Mahallesi", 7.5, 6.0, 2.5, 3.0, 7.5)
        );

        // Maliyet ve sosyal fayda analizlerini ayrı ayrı yap
        System.out.println("\n--- Maliyet Analizi ---");
        analyzeMaliyet(mahalleler);

        System.out.println("\n--- Sosyal Fayda Analizi ---");
        analyzeSosyalFayda(mahalleler);

        // En iyi mahalleyi seç
        Mahalle enUygunMahalle = softmaxMahalle(mahalleler);
        System.out.println("\nEn uygun güzergah: " + enUygunMahalle.ad);
    }

    // Softmax fonksiyonu
    private static double[] softmax(double[] scores) {
        double sumExp = Arrays.stream(scores).map(Math::exp).sum();
        return Arrays.stream(scores).map(s -> Math.exp(s) / sumExp).toArray();
    }

    // En iyi mahalleyi belirleyen metod
    private static Mahalle softmaxMahalle(List<Mahalle> mahalleler) {
        softmaxNormalize(mahalleler);
        return Collections.max(mahalleler, Comparator.comparing(m -> m.toplamSkor));
    }

    // Softmax normalizasyonu
    private static void softmaxNormalize(List<Mahalle> mahalleler) {
        double[] nufus = mahalleler.stream().mapToDouble(m -> m.nufusYogunlugu).toArray();
        double[] ulasim = mahalleler.stream().mapToDouble(m -> m.ulasimAltyapisi).toArray();
        double[] maliyet = mahalleler.stream().mapToDouble(m -> 1 / m.maliyet).toArray(); // Düşük maliyet avantajlı
        double[] cevresel = mahalleler.stream().mapToDouble(m -> m.cevreselEtki).toArray();
        double[] sosyal = mahalleler.stream().mapToDouble(m -> m.sosyalFayda).toArray();

        double[] softNufus = softmax(nufus);
        double[] softUlasim = softmax(ulasim);
        double[] softMaliyet = softmax(maliyet);
        double[] softCevresel = softmax(cevresel);
        double[] softSosyal = softmax(sosyal);

        for (int i = 0; i < mahalleler.size(); i++) {
            mahalleler.get(i).toplamSkor = softNufus[i] + softUlasim[i] + softMaliyet[i] + softCevresel[i] + softSosyal[i];
        }

        System.out.println("\nMahalle Skorları:");
        mahalleler.forEach(m -> System.out.println(m.ad + " - Toplam Skor: " + String.format("%.3f", m.toplamSkor)));
    }

    // Maliyet analizini yapan metod
    private static void analyzeMaliyet(List<Mahalle> mahalleler) {
        double[] maliyetler = mahalleler.stream().mapToDouble(m -> 1 / m.maliyet).toArray(); // Düşük maliyet avantajlı
        double[] softMaliyet = softmax(maliyetler);

        for (int i = 0; i < mahalleler.size(); i++) {
            System.out.println(mahalleler.get(i).ad + " - Maliyet Skoru: " + String.format("%.3f", softMaliyet[i]));
        }
    }

    // Sosyal fayda analizini yapan metod
    private static void analyzeSosyalFayda(List<Mahalle> mahalleler) {
        double[] sosyalFaydalar = mahalleler.stream().mapToDouble(m -> m.sosyalFayda).toArray();
        double[] softSosyal = softmax(sosyalFaydalar);

        for (int i = 0; i < mahalleler.size(); i++) {
            System.out.println(mahalleler.get(i).ad + " - Sosyal Fayda Skoru: " + String.format("%.3f", softSosyal[i]));
        }
    }
}
