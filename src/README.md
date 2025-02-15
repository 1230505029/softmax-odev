Softmax Mahalle Seçimi

Bu proje, bir bölgedeki mahalleleri değerlendirerek en uygun mahalleyi belirlemek için Softmax algoritmasını kullanır. Değerlendirme, nüfus yoğunluğu, ulaşım altyapısı, maliyet, çevresel etki ve sosyal fayda gibi kriterlere dayanmaktadır.

Proje Yapısı

Mahalle.java: Mahalle nesnesini tanımlar ve ilgili değişkenleri içerir.

SoftmaxMahalle.java: Softmax hesaplamalarını yaparak en uygun mahalleyi belirler.

Softmax Hesaplaması

Softmax fonksiyonu aşağıdaki gibi hesaplanmaktadır:



Bu fonksiyon, verileri normalize ederek her mahalleye bir skor atanmasını sağlar.

Analizler

Maliyet Analizi: Düşük maliyetli mahallelerin avantajlı olması için ters oran kullanılmıştır (1/maliyet).

Sosyal Fayda Analizi: Mahallelerin sosyal fayda skorları hesaplanarak değerlendirilmiştir.

Toplam Skor: Tüm faktörler softmax ile normalize edilip toplanarak en uygun mahalle belirlenir.