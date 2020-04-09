import java.util.*;

class game {


    /* * * * * * * * * * * * * * * * * * * * * * * * * * *
    ** 
    **
    **      Humans game.
    **
    **/

    public static void playHumanVSHuman(int[][] tray) {
        
        // Declarations.
        
        Random rand = new Random();
        boolean passPreviousTurn = false;
        int[] coordTab = new int[2];
        String team1, team2;
        boolean turn = true;
        String buffer = "";
        int buffern,colorteam1,colorteam2;
        Scanner sc = new Scanner(System.in);
        boolean gameStillGoingOn = true;
        int fautedefrappe = 0;
        int[][]playableBoxes = new int[tray.length][tray.length];

        // Game starts.

        System.out.println("# PROGRAM START");
        System.out.print("\n# J1 ROLL A DIE, TYPE 'D' : ");
        
        // Roll die J1.
        
        while (true) {
            char d = sc.next().charAt(0); 
            if (d == 'D')
                break;
            else {
                System.out.print("\n# ROLL A DIE, TYPE 'D' :");
                continue;
            }
        }
        int r = rand.nextInt(101);
        System.out.println("# YOU HAD A : " + r + " / 100");
        
        // Roll die J2.
        
        System.out.print("# J2 ROLL A DIE, TYPE 'D' : ");
        while (true) {
            char d = sc.next().charAt(0); 
            if (d == 'D')
                break;
            else {
                System.out.print("\n# ROLL A DIE, TYPE 'D' :");
                continue;
            }
        }
        int rh = rand.nextInt(101);
        System.out.println("\n# YOU HAD A : " + rh + " / 100");
        
        // Team <-> color association.
        
        if (rh <= r) {
            team1 = "BLACK";
            team2 = "WHITE";
            colorteam1 = 1;
            colorteam2 = 2;
            System.out.println("# J1 YOU ARE BLACK PLAYER\n# J2 YOU ARE WHITE PLAYER");
        }
        else {
            team1 = "WHITE";
            team2 = "BLACK";
            colorteam1 = 2;
            colorteam2 = 1;
            turn = false;
            System.out.println("# J1 YOU ARE WHITE PLAYER\n# J2 YOU ARE BLACK PLAYER");
        }


        // Partie dure tant que gameStillGoingOn = true.

        while(gameStillGoingOn) {

            Tray.display_tray(tray);
            waiting(1000);

            // Turn Player1

            if(turn == true) {

                playableBoxes = ia.test_playableBoxes(tray, colorteam1);                
                if (ia.noPlaceToPlay(tray, colorteam1) && passPreviousTurn) {
                    gameStillGoingOn = false;
                    System.out.println("GAME OVER");
                    Tray.display_tray(tray);
                    display_scores(tray);
                    continue;
                }
                if (ia.noPlaceToPlay(tray, colorteam1)){
                    System.out.println("# YOU CANT PLAY, THERE IS NO ROOM, YOU PASS");
                    System.out.println("PASS");
                    passPreviousTurn = true;
                    turn = false;
                    continue;
                }

                // Apres verification que le tray a encore de la place. Entrez coordonnées.

                System.out.println("# TURN " + team1 + "\n# TYPIN COORDINATES (a-z/1-26) or PASS:");
                buffer = sc.next();

                // Si 2 faute de frappes, fin de la partie.

                buffern = sc.nextInt();
                coordTab = ia.convertCoordArray(buffer,buffern);
                if (ia.casejouable(tray, coordTab[0], coordTab[1], colorteam1) == false && fautedefrappe == 1) {
                    gameStillGoingOn = false;
                    System.out.println("# CASE INJOUABLE, VOUS AVEZ PERDU");
                    continue;
                }
                if (ia.casejouable(tray, coordTab[0], coordTab[1], colorteam1) == false) {
                    fautedefrappe++;
                    System.out.println("# CASE INJOUABLE, VOUS AVEZ ENCORE UNE CHANCE;");
                    continue;
                }

                // Allez si tout se passe bien.

                else {
                    tray = Tray.reverse_tray(tray, coordTab[0], coordTab[1], colorteam1);
                    turn = false;
                    passPreviousTurn = false;
                    fautedefrappe = 0; 
                    continue;
                }
            }
            if (turn == false) {     

                // Turn Player2
                 
                if (ia.noPlaceToPlay(tray, colorteam2) && passPreviousTurn) {
                    gameStillGoingOn = false;
                    System.out.println("GAME OVER");
                    Tray.display_tray(tray);
                    display_scores(tray);
                    continue;
                }
                if (ia.noPlaceToPlay(tray, colorteam2)){
                    System.out.println("# YOU CANT PLAY, THERE IS NO ROOM, YOU PASS");
                    System.out.println("PASS");
                    passPreviousTurn = true;
                    turn = true;
                    continue;
                }

                // Apres verification que le tray a encore de la place. Entrez coordonnées.

                System.out.println("# TURN " + team2 + "\n# TYPIN COORDINATES (a-z/1-26) or PASS:");
                buffer = sc.next();

                // Si 2 faute de frappes, fin de la partie.

                buffern = sc.nextInt();
                coordTab = ia.convertCoordArray(buffer,buffern);
                if (ia.casejouable(tray, coordTab[0], coordTab[1], colorteam2) == false && fautedefrappe == 1) {
                    gameStillGoingOn = false;
                    System.out.println("# CASE INJOUABLE, VOUS AVEZ PERDU");
                    continue;
                }
                if (ia.casejouable(tray, coordTab[0], coordTab[1], colorteam2) == false) {
                        fautedefrappe++;
                        System.out.println("# CASE INJOUABLE, VOUS AVEZ ENCORE UNE CHANCE;");
                        continue;
                }
                
                // Passage au coup suivant.

                else {
                    tray = Tray.reverse_tray(tray, coordTab[0], coordTab[1], colorteam2);
                    turn = true;
                    passPreviousTurn = false;
                    fautedefrappe = 0; 
                    continue;
                }
            }
        }
    }


    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    **
    **
    **      Fonction de jeu contre CPU
    **
    **/

    public static void jouerpartiehumaincontremachine(int[][] tray) {        
        
        // Déclaration des variables de fonctionnement.
        
        Random rand = new Random();
        boolean passPreviousTurn = false;
        int[] coordTab = new int[2];
        String team1, team2;
        boolean turn = true;
        String buffer = "";
        int buffern,colorteam1,colorteam2;
        Scanner sc = new Scanner(System.in);
        boolean gameStillGoingOn = true;
        int fautedefrappe = 0;

        // Démarrage de la partie

        System.out.println("# PROGRAM START");
        waiting(1000);
        System.out.print("\n# J1 ROLL A DIE, TYPE 'D' : ");
        
        // Roll die J1.
        
        while (true) {
            char d = sc.next().charAt(0); 
            if (d == 'D')
                break;
            else {
                System.out.print("# ROLL A DIE, TYPE 'D' :");
                continue;
            }
        } 
        int r = rand.nextInt(101);
        System.out.println("# YOU HAD A : " + r + " / 100\n");
        waiting(1000);

        // Roll die MACHINE.
        
        System.out.print("# MACHINE TIRE UN D : \n");
        waiting(300);
        int rh = rand.nextInt(101);
        System.out.println("# MACHINE TIRE UN : " + rh + " / 100\n");

        // Team <-> color association.  

        if (rh <= r) {
            team1 = "BLACK";
            team2 = "WHITE";
            colorteam1 = 1;
            colorteam2 = 2;
            System.out.println("# J1 YOU ARE " + team1 + " PLAYER\n# MACHINE YOU ARE " + team2 + " PLAYER");
        }
        else {
            team1 = "WHITE";
            team2 = "BLACK";
            colorteam1 = 2;
            colorteam2 = 1;
            turn = false;
            System.out.println("# J1 YOU ARE " + team1 + " PLAYER\n# MACHINE YOU ARE " + team2 + " PLAYER");
        }

        waiting(2000);

        // Partie dure tant que gameStillGoingOn = true.

        while(gameStillGoingOn) {
            
            Tray.display_tray(tray);
            waiting(1000);

            // Human turn

            if(turn == true) {        
                
                if (ia.noPlaceToPlay(tray, colorteam1) && passPreviousTurn) {
                    gameStillGoingOn = false;
                    System.out.println("GAME OVER");
                    Tray.display_tray(tray);
                    display_scores(tray);
                    continue;
                }
                if (ia.noPlaceToPlay(tray, colorteam1)){
                    System.out.println("# YOU CANT PLAY, THERE IS NO ROOM, YOU PASS");
                    System.out.println("PASS");
                    passPreviousTurn = true;
                    waiting(1000);
                    turn = false;
                    continue;
                }

                // Apres verification que le tray a encore de la place. Entrez coordonnées.

                System.out.println("# TURN " + team1 + "\n# TYPIN COORDINATES (a-z/1-26) or PASS:");
                waiting(1000);
                buffer = sc.next();


                // Si 2 faute de frappes, fin de la partie.

                buffern = sc.nextInt();
                coordTab = ia.convertCoordArray(buffer,buffern);
                if (ia.casejouable(tray, coordTab[0], coordTab[1], colorteam1) == false && fautedefrappe == 1) {
                    gameStillGoingOn = false;
                    System.out.println("# CASE INJOUABLE, VOUS AVEZ PERDU");
                    continue;
                }
                if (ia.casejouable(tray, coordTab[0], coordTab[1], colorteam1) == false) {
                    fautedefrappe++;
                    System.out.println("# CASE INJOUABLE, VOUS AVEZ ENCORE UNE CHANCE;");
                    continue;
                }

                // Passage coup suivant.

                else {
                    tray = Tray.reverse_tray(tray, coordTab[0], coordTab[1], colorteam1);
                    turn = false;
                    passPreviousTurn = false;
                    fautedefrappe = 0; 
                    continue;
                }
            }
            if (turn == false) {     
                
                // Tour MACHINE
                 
                if (ia.noPlaceToPlay(tray, colorteam2) && passPreviousTurn) {
                    gameStillGoingOn = false;
                    System.out.println("GAME OVER");
                    Tray.display_tray(tray);
                    display_scores(tray);
                    continue;
                }
                if (ia.noPlaceToPlay(tray, colorteam2)){
                    System.out.println("# YOU CANT PLAY, THERE IS NO ROOM, YOU PASS");
                    System.out.println("PASS");
                    passPreviousTurn = true;            
                    waiting(1000);
                    turn = true;
                    continue;
                }

                // Apres verification que le tray a encore de la place. Entrez coordonnées.

                System.out.println("# TURN " + team2 + "\n# PC PLAYS....:");
                waiting(1000);

                // Calcul coup de l'IA
                int[][] newtray = new int [tray.length][tray.length];
                newtray = Tray.copy_tray(tray);
                coordTab = MinMax.coupMinMax(newtray, colorteam2);

                // Sortie formatée du coup IA

                System.out.println(ia.convertIntoCoord(coordTab));
                waiting(1000);

                // Passage coup suivant.
                tray = Tray.reverse_tray(tray, coordTab[0], coordTab[1], colorteam2);
                turn = true;
                passPreviousTurn = false;
                fautedefrappe = 0; 
                continue;
            
            }
        }
    }


    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    ** 
    ** 
    **  Cpu vs Cpu Local
    ** 
    ** 
    **/


    public static void CPUVSCPU(int[][] tray) {  
        
        // Déclaration des variables de fonctionnement.
        
        Random rand = new Random();
        boolean passPreviousTurn = false;
        int[] coordTab = new int[2];
        String team1, team2;
        boolean turn = true;
        int colorteam1,colorteam2;
        boolean gameStillGoingOn = true;

        // Démarrage de la partie

        System.out.println("# PROGRAM START");
        waiting(1000);

        System.out.println("# TIRAGE DES DES...\n");
        waiting(1000);

        // Roll die MACHINES.

        int r = rand.nextInt(101);
        System.out.println("# MACHINE1 TIRE UN : " + r + " / 100\n");
        waiting(2000);
        int rh = rand.nextInt(101);
        System.out.println("# MACHINE2 TIRE UN : " + rh + " / 100\n");
        waiting(2000);
        
        // Team <-> color association.
        
        if (rh <= r) {
            team1 = "BLACK";
            team2 = "WHITE";
            colorteam1 = 1;
            colorteam2 = 2;
            System.out.println("# MACHINE1 YOU ARE " + team1 + " PLAYER\n# MACHINE2 YOU ARE " + team2 + " PLAYER");
        }
        else {
            team1 = "WHITE";
            team2 = "BLACK";
            colorteam1 = 2;
            colorteam2 = 1;
            turn = false;
            System.out.println("# MACHINE1 YOU ARE " + team1 + " PLAYER\n# MACHINE2 YOU ARE " + team2 + " PLAYER");
        }

        waiting(3000);

        // Partie dure tant que gameStillGoingOn = true.

        while(gameStillGoingOn) {
            Tray.display_tray(tray);
            waiting(2000);
   
            // Turn MACHINE1

            if(turn == true) {
                
                if (ia.noPlaceToPlay(tray, colorteam1) && passPreviousTurn) {
                    gameStillGoingOn = false;
                    System.out.println("GAME OVER");
                    Tray.display_tray(tray);
                    display_scores(tray);
                    continue;
                }
                if (ia.noPlaceToPlay(tray, colorteam1)){
                    System.out.println("# YOU CANT PLAY, THERE IS NO ROOM, YOU PASS");
                    System.out.println("PASS");
                    passPreviousTurn = true;
                    turn = false;
                    continue;
                }

                // Apres verification que le tray a encore de la place. Entrez coordonnées.

                System.out.println("# TURN " + team1 + " (TEAM MINMAX)\n# TYPIN COORDINATES (a-z/1-26):");
                waiting(1000);

                // Calcul coup de l'IA
                coordTab = MinMax.coupMinMax(tray, colorteam1);

                // Sortie formatée du coup IA

                System.out.println(ia.convertIntoCoord(coordTab));
                waiting(1000);

                // Passage coup suivant.

                tray = Tray.reverse_tray(tray, coordTab[0], coordTab[1], colorteam1);
                turn = false;
                passPreviousTurn = false;
                continue;
            
            }

            // Team MACHINE2

            if (turn == false) {     
                 
                if (ia.noPlaceToPlay(tray, colorteam2) && passPreviousTurn) {
                    gameStillGoingOn = false;
                    System.out.println("GAME OVER");
                    Tray.display_tray(tray);
                    display_scores(tray);
                    continue;
                }
                if (ia.noPlaceToPlay(tray, colorteam2)){
                    System.out.println("# YOU CANT PLAY, THERE IS NO ROOM, YOU PASS");
                    System.out.println("PASS");
                    passPreviousTurn = true;
                    turn = true;
                    continue;
                }

                // Apres verification que le tray a encore de la place. Entrez coordonnées.

                System.out.println("# TURN " + team2 + " (TEAM CLASSIC)\n# TYPIN COORDINATES (a-z/1-26):");
                waiting(1000);

                // Calcul coup de l'IA
                coordTab = ia.iaPlay(tray, colorteam2);

                // Sortie formatée du coup IA

                System.out.println(ia.convertIntoCoord(coordTab));
                waiting(1000);

                // Passage coup suivant.

                tray = Tray.reverse_tray(tray, coordTab[0], coordTab[1], colorteam2);
                turn = true;
                passPreviousTurn = false;
                continue;
            }
        }
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    ** 
    ** 
    **  Game generation tools 
    ** 
    ** 
    **/

    public static void genereretjouerlapartiecontrehumain() {
        Scanner sc = new Scanner(System.in);
        int buff = 0;
        boolean start = true;
        while(start) {
            System.out.println("#GENERATION tray...\n#DEFINE EVEN tray.LENGTH -> DEFAULT : 8;");
            buff = sc.nextInt();
            if (buff%2 != 0 || buff > 26) {
                System.out.println("Veuillez saisir une valeur paire [0-26]");
                continue;
            }
            playHumanVSHuman(Tray.newGame(buff));
            waiting(2000);
            System.out.print("Want to play another game (Y/N)? : ");
            while (true) {
                char yesorno = sc.next().charAt(0); 
                if (yesorno == 'Y')
                    break;
                else if (yesorno == 'N') {
                    System.out.println("Have a nice day !");
                    start = false;
                    break;
                }
                else {
                    System.out.println("Not a choice; try another shot (Y/N)?");
                    continue;
                }
            }
        }
        sc.close();
    }

    public static void genereretjouerPCUVSPCU() {
        int buff = 8;
        Scanner sc = new Scanner(System.in);
        boolean start = true;
        while(start) {
            System.out.println("#GENERATION tray...\n#SET LENGTH TO 8 ;");
            CPUVSCPU(Tray.newGame(buff));
            waiting(2000);
            System.out.print("WANT TO WATCH ANOTHER GAME (Y/N)? : ");
            while (true) {
                char yesorno = sc.next().charAt(0); 
                if (yesorno == 'Y')
                    break;
                else if (yesorno == 'N') {
                    System.out.println("HAVE A NICE DAY !");
                    start = false;
                    break;
                }
                else {
                    System.out.println("NOT A VALID CHOICE, WATCH ANOTHER GAME (Y/N)?");
                    continue;
                }
            }
        }
        sc.close();
    }

    public static void genereretjouerlapartiecontremachine() {
        Scanner sc = new Scanner(System.in);
        int buff = 0;
        boolean start = true;
        while(start) {
            System.out.println("#GENERATION tray...\n#DEFINE EVEN tray.LENGTH -> DEFAULT : 8;");
            buff = sc.nextInt();
            if (buff%2 != 0 || buff > 26) {
                System.out.println("Veuillez saisir une valeur paire [0-26]");
                continue;
            }
            jouerpartiehumaincontremachine(Tray.newGame(buff));
            waiting(2000);
            System.out.print("Want to play another game (Y/N)? : ");
            while (true) {
                char yesorno = sc.next().charAt(0); 
                if (yesorno == 'Y')
                    break;
                else if (yesorno == 'N') {
                    System.out.println("Have a nice day !");
                    start = false;
                    break;
                }
                else {
                    System.out.println("Not a choice; try another shot (Y/N)?");
                    continue;
                }
            }
        }
        sc.close();
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    ** 
    ** 
    **  Tools 
    ** 
    ** 
    **/


    public static void waiting(int x) 
    {
        try {
            Thread.sleep(x); // Temps d'attente
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }

    public static void display_scores(int[][] tray) {
        int scoreBlanc = 0;
        int scoreNoir = 0;
        int sizetray = tray.length;
        for (int i = 0; i < sizetray; i++) {
            for (int j = 0; j < sizetray; j++) {
                if (tray[i][j] == 1)
                    scoreNoir++;
                if (tray[i][j] == 2)
                    scoreBlanc++;
            }
        }
        System.out.println("\n#SCORE NOIR = "+ scoreNoir +"\n#SCORE BLANC = " + scoreBlanc);
    }

    public static int[] scores(int[][] tray) {
        int scoreBlanc = 0;
        int scoreNoir = 0;
        int sizetray = tray.length;
        for (int i = 0; i < sizetray; i++) {
            for (int j = 0; j < sizetray; j++) {
                if (tray[i][j] == 1)
                    scoreNoir++;
                if (tray[i][j] == 2)
                    scoreBlanc++;
            }
        }
        int[] scores = new int[2];
        scores[0] = scoreNoir;
        scores[1] = scoreBlanc;
        return scores;   
    }
}
