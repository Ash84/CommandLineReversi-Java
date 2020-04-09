class Tray {


    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    ** 
    ** 
    **     Display fuctions for tray
    **
    **
    **/


    public static String numberToLetter(int n) 
    {    
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String letter = alphabet.substring(n, n+1);
        return letter;
    }
    
    public static void display_vectors(int[][] tray) 
    {
        int size = tray.length;
        int temp;
        String layout = "";
        for (int i = 0; i < size ; i++) {
            temp = i+1;
            if (size > 9 && i < 9)
                layout = " ";
            else 
                layout = "";
            System.out.print("\n# "+ temp + layout + " [ ");
            for (int j = 0; j < size; j++) 
            {
                System.out.print(tray[j][i] + " ");
            }
            System.out.print("]");
        }
        System.out.print("\n#     ");
        if (size > 9)
                System.out.print(" ");
        for (int i = 0; i < size; i++)
        {
            System.out.print(numberToLetter(i) + " ");
        }
        System.out.println();
    }

    public static void display_tray(int[][] tray) 
    {
        int size = tray.length;
        int temp;
        String buffer = "";
        String layout = "";
        for (int i = 0; i < size ; i++) {
            temp = i+1;
            if (size > 9 && i < 9)
                layout = " ";
            else 
                layout = "";
            System.out.print("\n# "+ temp + layout + " [ ");
            for (int j = 0; j < size; j++) 
            {
                if (tray[j][i] == 0)
                    buffer = " ";
                if (tray[j][i] == 1)
                    buffer = "◎";
                if (tray[j][i] == 2)
                    buffer = "●";
                System.out.print(buffer + " ");
            }
            System.out.print("]");
        }
        System.out.print("\n#     ");
        if (size > 9)
                System.out.print(" ");
        for (int i = 0; i < size; i++)
        {
            System.out.print(numberToLetter(i) + " ");
        }
        System.out.println();
    }







    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    ** 
    **
    **      Tray exploration and setting functions.
    **
    **
    **/ 


    public static int[][] newGame(int sizetray) {
        int[][] tray = new int[sizetray][sizetray];
        for (int i = 0; i < sizetray; i++) {
            for (int j = 0; j < sizetray; j++) {
                tray[i][j] = 0;
            }
        }
        tray [sizetray/2][sizetray/2-1] = 1;
        tray [sizetray/2-1][sizetray/2] = 1;
        tray [sizetray/2-1][sizetray/2-1] = 2;
        tray [sizetray/2][sizetray/2] = 2;
        return tray;
    }


    public static int testBox(int[][] tray, int x, int y) {
        int color = tray[x][y];
        return color;
    }

    // Teste la diagonale vers le bas et la gauche
    public static int testdiagversbasgauche(int[][] tray, int x, int y, int yourteam) {
        int i = 2;
        int bord = (tray.length-1)-y;
        int bordg = x;
        if (y == (tray.length-1) || x == 0 ||  tray[x-1][y+1] == 0 || tray[x-1][y+1] == yourteam)
            return 0;
        while(true) {
            if (i > bord || i > bordg)
                break;
            if (tray[x-i][y+i] == yourteam)
                return i-1;
            if (tray[x-i][y+i] == 0)
                break;
            if (tray[x-i][y+i] != yourteam) {
                i++;
                continue;
            }
        }  
        return 0;
    }

    // Retourne 0 Si la colonne n'est pas jouable, retourne int nombre de cases avant un pion de son équipe qui sandwitche des pions adverse vers le bas.
    public static int testcolonneverslebas(int[][] tray, int x, int y, int yourteam) {
        int i = 2;
        int bord = (tray.length-1)-y;
        if (y >= tray.length-1 || tray[x][y+1] == 0 || tray[x][y+1] == yourteam)
            return 0;
        while(true) {
            if (i > bord)
                break;
            if (tray[x][y+i] == yourteam)
                return i-1;
            if (tray[x][y+i] == 0)
                break;
            if (tray[x][y+i] != yourteam) {
                i++;
                continue;
            }
        }  
        return 0;
    }

    // Teste la diagonale vers le bas et la droite
    public static int testdiagversbasdroite(int[][] tray, int x, int y, int yourteam) {
        int i = 2;
        int bord = (tray.length-1)-y;
        int bordd = (tray.length-1)-x;
        if (y >= tray.length-1 || x >= tray.length-1 || tray[x+1][y+1] == 0 || tray[x+1][y+1] == yourteam)
            return 0;
        while(true) {
            if (i > bord || i > bordd)
                break;
            if (tray[x+i][y+i] == yourteam)
                return i-1;
            if (tray[x+i][y+i] == 0)
                break;
            if (tray[x+i][y+i] != yourteam) {
                i++;
                continue;
            }
        }
        return 0;
    }


    // Teste la diagonale vers le haut et la gauche
    public static int testdiagvershautgauche(int[][] tray, int x, int y, int yourteam) {
        int i = 2;
        int bord = x;
        int bordh = y;
        if (y == 0 || x == 0 || tray[x-1][y-1] == 0 ||tray[x-1][y-1] == yourteam)
            return 0;
        while(true) {
            if (i > bord || i > bordh)
                break;
            if (tray[x-i][y-i] == yourteam)
                return i-1;
            if (tray[x-i][y-i] == 0)
                break;
            if (tray[x-i][y-i] != yourteam) {
                i++;
                continue;
            }
        }  
        return 0;
    }

    public static int testcolonneverslehaut(int[][] tray, int x, int y, int yourteam) {
        int i = 2;
        int bord = y;
        if (y == 0 || tray[x][y-1] == 0 || tray[x][y-1] == yourteam)
            return 0;
        while(true) {
            if (i > bord)
                break;
            if (tray[x][y-i] == yourteam)
                return i-1;
            if (tray[x][y-i] == 0)
                break;
            if (tray[x][y-i] != yourteam) {
                i++;
                continue;
            }    
        }  
        return 0;
    }

    // Teste la diagonale vers le haut et la droite
    public static int testdiagvershautdroite(int[][] tray, int x, int y, int yourteam) {
        int i = 2;
        int bord = y;
        int bordd = (tray.length-1)-x;
        if (y == 0 || x == (tray.length-1) || tray[x+1][y-1] == 0 || tray[x+1][y-1] == yourteam)
            return 0;
        while(true) {
            if (i > bord || i > bordd)
                break;
            if (tray[x+i][y-i] == yourteam)
                return i-1;
            if (tray[x+i][y-i] == 0)
                break;
            if (tray[x+i][y-i] != yourteam) {
                i++;
                continue;
            }
        }  
        return 0;
    }

    // Retourne 0 Si la ligne n'est pas jouable, retourne int nombre de cases avant un pion de son équipe qui sandwitche des pions adverse vers la gauche.
    public static int testligneverslagauche(int[][] tray, int x, int y, int yourteam) {
        int i = 2;
        int bord = x;
        if ( x == 0 || tray[x-1][y] == 0 || tray[x-1][y] == yourteam)
            return 0;
        while(true) {
            if (i > bord)
                break;
            if (tray[x-i][y] == yourteam)
                return i-1;
            if (tray[x-i][y] == 0)
                break;
            if (tray[x-i][y] != yourteam) {
                i++;
                continue;
            }
        }  
        return 0;
    }


    // Retourne 0 Si la ligne n'est pas jouable, retourne int nombre de cases avant un pion de son équipe qui sandwitche des pions adverse vers la droite.
    public static int testligneversladroite(int[][] tray, int x, int y, int yourteam) {
        int i = 2;
        int bord = (tray.length-1) - x;
        if (x >= tray.length-1 || tray[x+1][y] == 0 || tray[x+1][y] == yourteam)
            return 0;
        while(true) { 
            if (i > bord)
                break;   
            if (tray[x+i][y] == yourteam)
                return i-1;
            if (tray[x+i][y] == 0)
                break;
            if (tray[x+i][y] != yourteam) {
                i++;
                continue;
            }
        }  
        return 0;
    }


    // Calcul du tray à t+1 après le coup.

    public static int[][] reverse_tray(int tray[][], int x, int y, int yourteam) {
        int[][] trayTplusun = copy_tray(tray);
        int ndbg = testdiagversbasgauche(trayTplusun, x, y, yourteam);
        if (ndbg > 0)
            for (int i = 0; i <= ndbg; i++) {
                trayTplusun[x-i][y+i] = yourteam;
            }
        int ncb = testcolonneverslebas(trayTplusun, x, y, yourteam);
        if (ncb > 0)
            for (int i = 0; i <= ncb; i++) {
                trayTplusun[x][y+i] = yourteam;
            }
        int ndd = testdiagversbasdroite(trayTplusun, x, y, yourteam);
        if (ndd > 0)
            for (int i = 0; i <= ndd; i++) {
                trayTplusun[x+i][y+i] = yourteam;
            }
        int ndhg = testdiagvershautgauche(trayTplusun, x, y, yourteam);
        if (ndhg > 0)
            for (int i = 0; i <= ndhg; i++) {
                trayTplusun[x-i][y-i] = yourteam;
            }
        int nch = testcolonneverslehaut(trayTplusun, x, y, yourteam);
        if (nch > 0)
            for (int i = 0; i <= nch; i++) {
                trayTplusun[x][y-i] = yourteam;
            }
        int ndhd = testdiagvershautdroite(trayTplusun, x, y, yourteam);
        if (ndhd > 0)
            for (int i = 0; i <= ndhd; i++) {
                trayTplusun[x+i][y-i] = yourteam;
            }
        int nlg = testligneverslagauche(trayTplusun, x, y, yourteam);
        if (nlg > 0)
            for (int i = 0; i <= nlg; i++) {
                trayTplusun[x-i][y] = yourteam;
            }
        int nld = testligneversladroite(trayTplusun, x, y, yourteam);
        if (nld > 0)
            for (int i = 0; i <= nld; i++) {
                trayTplusun[x+i][y] = yourteam;
            }
        return trayTplusun;
    }

    public static int[][] copy_tray(int[][] tray) {
        int size = tray.length;
        int[][] cpy = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cpy[i][j] = tray[i][j]; 
            }
        }
        return cpy;
    }
}