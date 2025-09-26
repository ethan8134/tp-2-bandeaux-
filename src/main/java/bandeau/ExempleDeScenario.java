package bandeau;

public class ExempleDeScenario {

    /**
     * "Programme principal" : point d'entrée d'exécution
     *
     * @param args les "arguments de ligne de commande", transmis au lancement du programme
     */
    public static void main(String[] args) {
        String message;
        if (args.length > 0) // J'ai au moins un paramètre
        {
            message = args[0]; // le premier paramètre
        } else {
            message = "Démonstration du bandeau";
        }

        // On cree un scenario
        // On lui ajoute des effets
        Scenario s1 = new Scenario();
        s1.addEffect(new RandomEffect(message, 700), 1);
        s1.addEffect(new TeleType("Je m'affiche caractère par caractère", 100), 1);
        s1.addEffect(new Blink("Je clignote 10x", 100), 10);
        s1.addEffect(new Zoom("Je zoome", 50), 1);
        s1.addEffect(new FontEnumerator(10), 1);
        s1.addEffect(new Rainbow("Comme c'est joli !", 30), 1);
        s1.addEffect(new Rotate("2 tours à droite", 180, 4000, true), 2);

        Scenario s2 = new Scenario();
        s2.addEffect(new RandomEffect(message, 700), 1);
        s2.addEffect(new TeleType("Je m'affiche caractère par caractère", 100), 1);
        s2.addEffect(new Blink("Je clignote 10x", 100), 10);

        Scenario s3 = new Scenario();
        s3.addEffect(new RandomEffect(message, 700), 1);
        s3.addEffect(new FontEnumerator(10), 1);
        s3.addEffect(new Rainbow("Comme c'est joli !", 30), 1);
        s3.addEffect(new Rotate("2 tours à droite", 180, 4000, true), 2);
 
        
        // On cree les bandeaux
        Bandeau b1 = new Bandeau();
        Bandeau b2 = new Bandeau();
        Bandeau b3 = new Bandeau();
        System.out.println("CTRL+C pour terminer le programme");

        // On doit jouer le scénario en même temps sur plusieurs bandeaux :
        Thread thread1 = new Thread(() -> playScenarioSafely(s1, b1));
        Thread thread2 = new Thread(() -> playScenarioSafely(s2, b2));
        Thread thread3 = new Thread(() -> playScenarioSafely(s3, b3));

        thread1.start();
        thread2.start();
        thread3.start();

        // Temporisation avant modification
        try {
            Thread.sleep(10000); // Attendre 10 secondes avant de modifier le scénario
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * Joue un scénario sur un bandeau de manière sécurisée (un seul scénario à la fois par bandeau).
     *
     * @param scenario Le scénario à jouer
     * @param bandeau  Le bandeau sur lequel jouer le scénario
     */
    private static void playScenarioSafely(Scenario scenario, Bandeau bandeau) {
        synchronized (scenario) {
            scenario.playOn(bandeau);
        }
    }
}
