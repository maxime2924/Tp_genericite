import java.util.*;
import java.util.function.Predicate;

public class BibliothequeTest {
    public static void main(String[] args) {
        // Création de médias
        Livre livre1 = new Livre("Le Petit Prince", 1943, "Antoine de Saint-Exupéry", 96);
        Livre livre2 = new Livre("Java pour les Nuls", 2020, "Barry Burd", 400);
        CD cd1 = new CD("Thriller", 1982, "Michael Jackson", 42);
        CD cd2 = new CD("Random Access Memories", 2013, "Daft Punk", 74);

        // Création de membres
        Membre membre1 = new Membre("Alice", 1);
        Membre membre2 = new Membre("Bob", 2);

        // Emprunts
        membre1.emprunterMedia(livre1);
        membre1.emprunterMedia(cd1);
        membre2.emprunterMedia(livre2);
        membre2.emprunterMedia(cd2);

        // Collections
        List<Media> mediasDisponibles = Arrays.asList(livre1, livre2, cd1, cd2);
        Set<Membre> membres = new HashSet<>(Arrays.asList(membre1, membre2));
        Map<Membre, List<Media>> emprunts = new HashMap<>();
        emprunts.put(membre1, membre1.mediasEmpruntes);
        emprunts.put(membre2, membre2.mediasEmpruntes);

        // Affichage des médias disponibles
        System.out.println("=== Médias disponibles ===");
        afficherListe(mediasDisponibles);

        // Affichage des membres
        System.out.println("\n=== Membres ===");
        afficherListe(new ArrayList<>(membres));

        // Test de la méthode afficherDetails
        System.out.println("\n=== Détails des médias ===");
        for (Media media : mediasDisponibles) {
            media.afficherDetails();
        }

        // Test du filtrage
        System.out.println("\n=== Médias publiés après 2010 ===");
        List<Media> mediasRecents = filtrer(mediasDisponibles, m -> m.getAnneePublication() > 2010);
        afficherListe(mediasRecents);

        // Test du filtrage des membres dont le nom commence par "A"
        System.out.println("\n=== Membres dont le nom commence par 'A' ===");
        List<Membre> membresFiltres = filtrer(new ArrayList<>(membres), m -> m.getNom().startsWith("A"));
        afficherListe(membresFiltres);

        // Test du tri des médias
        System.out.println("\n=== Médias triés par année (décroissante) puis par titre ===");
        Comparator<Media> comparateurMedia = Comparator
            .comparingInt(Media::getAnneePublication).reversed()
            .thenComparing(Media::getTitre);
        Collections.sort(mediasDisponibles, comparateurMedia);
        afficherListe(mediasDisponibles);

        // Test du tri des livres par auteur puis par titre
        System.out.println("\n=== Livres triés par auteur puis par titre ===");
        List<Livre> livres = Arrays.asList(livre1, livre2);
        Comparator<Livre> comparateurLivre = Comparator
            .comparing(Livre::getAuteur)
            .thenComparing(Livre::getTitre);
        Collections.sort(livres, comparateurLivre);
        afficherListe(livres);

        // Test de la copie de collection
        System.out.println("\n=== Copie de collection ===");
        List<Media> copieMedias = new ArrayList<>();
        copierCollection(mediasDisponibles, copieMedias);
        System.out.println("Copie effectuée. Taille : " + copieMedias.size());

        // Test des médias empruntés sans doublons
        System.out.println("\n=== Médias empruntés (sans doublons) ===");
        Set<Media> mediasEmpruntesSansDoublons = getMediasEmpruntesSansDoublons(emprunts);
        afficherListe(new ArrayList<>(mediasEmpruntesSansDoublons));

        // Test du filtrage polymorphe (uniquement les livres)
        System.out.println("\n=== Filtrage polymorphe : uniquement les livres ===");
        List<Livre> livresFiltres = filtrerLivres(mediasDisponibles);
        afficherListe(livresFiltres);
    }

    // Méthode générique pour afficher une liste
    public static <T> void afficherListe(List<T> liste) {
        for (T element : liste) {
            System.out.println(element);
        }
    }

    // Méthode générique pour filtrer une liste
    public static <T> List<T> filtrer(List<T> liste, Predicate<T> critere) {
        List<T> resultat = new ArrayList<>();
        for (T element : liste) {
            if (critere.test(element)) {
                resultat.add(element);
            }
        }
        return resultat;
    }

    // Méthode générique pour copier une collection
    public static <T> void copierCollection(Collection<T> source, Collection<T> destination) {
        destination.addAll(source);
    }

    // Médias empruntés sans doublons
    public static Set<Media> getMediasEmpruntesSansDoublons(Map<Membre, List<Media>> emprunts) {
        Set<Media> medias = new HashSet<>();
        for (List<Media> liste : emprunts.values()) {
            medias.addAll(liste);
        }
        return medias;
    }

    // Filtrage polymorphe : uniquement les livres
    public static List<Livre> filtrerLivres(List<Media> medias) {
        List<Livre> livres = new ArrayList<>();
        for (Media media : medias) {
            if (media instanceof Livre) {
                livres.add((Livre) media);
            }
        }
        return livres;
    }
}
