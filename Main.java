import java.util.*;

enum KelasPenerbangan {
    EKONOMI,
    BISNIS,
    KELAS_PERTAMA
}

class Penerbangan {
    private String nomorPenerbangan;
    private String keberangkatan;
    private String tujuan;
    private String tanggal; 

    public Penerbangan(String nomorPenerbangan, String keberangkatan, String tujuan, String tanggal) {
        this.nomorPenerbangan = nomorPenerbangan;
        this.keberangkatan = keberangkatan;
        this.tujuan = tujuan;
        this.tanggal = tanggal; 
    }

    public String getNomorPenerbangan() {
        return nomorPenerbangan;
    }

    public String getKeberangkatan() {
        return keberangkatan;
    }

    public String getTujuan() {
        return tujuan;
    }

    public String getTanggal() {
        return tanggal;
    }
}


class Pesawat {
    private String kodePesawat;
    private String maskapai;

    public Pesawat(String kodePesawat, String maskapai) {
        this.kodePesawat = kodePesawat;
        this.maskapai = maskapai;
    }

    public String getKodePesawat() {
        return kodePesawat;
    }

    public String getMaskapai() {
        return maskapai;
    }
}

interface PenerbanganInterface {
    String getNomorPenerbangan();
    String getKeberangkatan();
    String getTujuan();
    String getTanggal();
    double getHargaTiket(KelasPenerbangan kelas);
    Pesawat getPesawat();
}

class PenerbanganKomersial extends Penerbangan implements PenerbanganInterface {
    private Pesawat pesawat;
    private Map<KelasPenerbangan, Integer> totalKursi;
    private Map<KelasPenerbangan, Integer> kursiTersedia;
    private Map<KelasPenerbangan, Double> hargaTiket;

    public PenerbanganKomersial(String nomorPenerbangan, String keberangkatan, String tujuan, String tanggal, Pesawat pesawat) {
        super(nomorPenerbangan, keberangkatan, tujuan, tanggal);
        this.pesawat = pesawat;
        this.totalKursi = new HashMap<>();
        this.kursiTersedia = new HashMap<>();
        this.hargaTiket = new HashMap<>();
        inisialisasiKursi();
        inisialisasiHargaTiket();
    }

    private void inisialisasiKursi() {
        totalKursi.put(KelasPenerbangan.EKONOMI, 100);
        totalKursi.put(KelasPenerbangan.BISNIS, 50);
        totalKursi.put(KelasPenerbangan.KELAS_PERTAMA, 20);
        kursiTersedia.putAll(totalKursi);
    }

    private void inisialisasiHargaTiket() {
        hargaTiket.put(KelasPenerbangan.EKONOMI, 100.0);
        hargaTiket.put(KelasPenerbangan.BISNIS, 250.0);
        hargaTiket.put(KelasPenerbangan.KELAS_PERTAMA, 500.0);
    }

    public int getTotalKursi(KelasPenerbangan kelas) {
        return totalKursi.getOrDefault(kelas, 0);
    }

    public int getKursiTersedia(KelasPenerbangan kelas) {
        return kursiTersedia.getOrDefault(kelas, 0);
    }

    public double getHargaTiket(KelasPenerbangan kelas) {
        return hargaTiket.getOrDefault(kelas, 0.0);
    }

    public boolean pesanKursi(KelasPenerbangan kelas) {
        if (kursiTersedia.getOrDefault(kelas, 0) > 0) {
            kursiTersedia.put(kelas, kursiTersedia.get(kelas) - 1);
            return true;
        } else {
            return false;
        }
    }

    public Pesawat getPesawat() {
        return pesawat;
    }
}

class Reservasi {
    private String kodeReservasi;
    private PenerbanganKomersial penerbangan;
    private KelasPenerbangan kelasPenerbangan;
    private String namaPenumpang;

    public Reservasi(String kodeReservasi, PenerbanganKomersial penerbangan, KelasPenerbangan kelasPenerbangan, String namaPenumpang) {
        this.kodeReservasi = kodeReservasi;
        this.penerbangan = penerbangan;
        this.kelasPenerbangan = kelasPenerbangan;
        this.namaPenumpang = namaPenumpang;
    }

    public String getKodeReservasi() {
        return kodeReservasi;
    }

    public PenerbanganKomersial getPenerbangan() {
        return penerbangan;
    }

    public KelasPenerbangan getKelasPenerbangan() {
        return kelasPenerbangan;
    }

    public String getNamaPenumpang() {
        return namaPenumpang;
    }
}

class ManajerReservasi {
    private List<Reservasi> reservasi;
    private List<PenerbanganKomersial> penerbanganList;
    private List<Pesawat> pesawatList;

    public ManajerReservasi() {
        this.reservasi = new ArrayList<>();
        this.penerbanganList = new ArrayList<>();
        this.pesawatList = new ArrayList<>();
    }

    public void tambahReservasi(Reservasi reservasi) {
        this.reservasi.add(reservasi);
    }

    public void tambahPenerbangan(PenerbanganKomersial penerbangan) {
        this.penerbanganList.add(penerbangan);
    }

    public void tambahPesawat(Pesawat pesawat) {
        this.pesawatList.add(pesawat);
    }

    public void tampilkanSemuaReservasi() {
        System.out.println("Semua Reservasi:");
        for (Reservasi reservasi : reservasi) {
            System.out.println("Kode Reservasi: " + reservasi.getKodeReservasi() +
                    ", Nama Penumpang: " + reservasi.getNamaPenumpang() +
                    ", Nomor Penerbangan: " + reservasi.getPenerbangan().getNomorPenerbangan() +
                    ", Keberangkatan: " + reservasi.getPenerbangan().getKeberangkatan() +
                    ", Tujuan: " + reservasi.getPenerbangan().getTujuan() +
                    ", Tanngal: " + reservasi.getPenerbangan().getTanggal() +
                    ", Kelas: " + reservasi.getKelasPenerbangan() +
                    ", Harga: $" + reservasi.getPenerbangan().getHargaTiket(reservasi.getKelasPenerbangan()) +
                    ", Maskapai: " + reservasi.getPenerbangan().getPesawat().getMaskapai());
        }
        System.out.println();
    }

    public void tampilkanSemuaPesawat() {
        System.out.println("Semua Pesawat:");
        for (Pesawat pesawat : pesawatList) {
            System.out.println("Kode Pesawat: " + pesawat.getKodePesawat() +
                    ", Maskapai: " + pesawat.getMaskapai());
        }
        System.out.println();
    }

    public void tampilkanSemuaPenerbangan() {
        System.out.println("Semua Penerbangan:");
        for (PenerbanganKomersial penerbangan : penerbanganList) {
            System.out.println("Nomor Penerbangan: " + penerbangan.getNomorPenerbangan() +
                    ", Keberangkatan: " + penerbangan.getKeberangkatan() +
                    ", Tujuan: " + penerbangan.getTujuan() +
                    ", Tanggal: " + penerbangan.getTanggal() +
                    ", Maskapai: " + penerbangan.getPesawat().getMaskapai());
        }
        System.out.println();
    }
}

public class Main {
    public static void main(String[] args) {
        Pesawat pesawat1 = new Pesawat("PW001", "Garuda Indonesia");
        Pesawat pesawat2 = new Pesawat("PW002", "Lion Air");

        PenerbanganKomersial penerbangan1 = new PenerbanganKomersial("ABC123", "Jakarta", "Surabaya", "2024-04-20", pesawat1);
        PenerbanganKomersial penerbangan2 = new PenerbanganKomersial("XYZ789", "Surabaya", "Jakarta", "2024-04-21", pesawat2);

        ManajerReservasi manajerReservasi = new ManajerReservasi();

        KelasPenerbangan kelasUntukReservasi1 = KelasPenerbangan.EKONOMI;
        KelasPenerbangan kelasUntukReservasi2 = KelasPenerbangan.BISNIS;
        KelasPenerbangan kelasUntukReservasi3 = KelasPenerbangan.KELAS_PERTAMA;
        
        Reservasi reservasi1 = new Reservasi("RES001", penerbangan1, kelasUntukReservasi1, "John Doe");
        Reservasi reservasi2 = new Reservasi("RES002", penerbangan2, kelasUntukReservasi2, "Jane Smith");
        Reservasi reservasi3 = new Reservasi("RES003", penerbangan1, kelasUntukReservasi1, "Alice Johnson");
        Reservasi reservasi4 = new Reservasi("RES004", penerbangan2, kelasUntukReservasi2, "Bob Anderson");
        Reservasi reservasi5 = new Reservasi("RES005", penerbangan1, kelasUntukReservasi3, "Charlie Brown");

        
        manajerReservasi.tambahPesawat(pesawat1);
        manajerReservasi.tambahPesawat(pesawat2);

        manajerReservasi.tambahPenerbangan(penerbangan1);
        manajerReservasi.tambahPenerbangan(penerbangan2);

        manajerReservasi.tambahReservasi(reservasi1);
        manajerReservasi.tambahReservasi(reservasi2);
        manajerReservasi.tambahReservasi(reservasi3);
        manajerReservasi.tambahReservasi(reservasi4);
        manajerReservasi.tambahReservasi(reservasi5);

        manajerReservasi.tampilkanSemuaPesawat();
        manajerReservasi.tampilkanSemuaPenerbangan();
        manajerReservasi.tampilkanSemuaReservasi();
    }
}
