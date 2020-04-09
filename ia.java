import java.util.*;

class ia {


    public static int[][] test_playableBoxes (int[][] plateau, int yourteam) {
        int size = plateau.length;
        int[][] coupsjouables = new int[size][size];
        int temp1, temp2, temp3, temp6, temp9, temp8, temp7, temp4;
        for (int i = 0; i < size ; i++) {
            for (int j = 0; j < size; j++) {
                if (plateau[i][j] != 0) {
                    coupsjouables[i][j] = 0; // case occupée, injouable
                    continue;
                }
                temp1 = Tray.testdiagversbasgauche(plateau, i, j, yourteam);
                temp2 = Tray.testcolonneverslebas(plateau, i, j, yourteam);
                temp3 = Tray.testdiagversbasdroite(plateau, i, j, yourteam);
                temp6 = Tray.testligneversladroite(plateau, i, j, yourteam);
                temp9 = Tray.testdiagvershautdroite(plateau, i, j, yourteam);
                temp8 = Tray.testcolonneverslehaut(plateau, i, j, yourteam);
                temp7 = Tray.testdiagvershautgauche(plateau, i, j, yourteam);
                temp4 = Tray.testligneverslagauche(plateau, i, j, yourteam);
                if (temp1 ==  0 && temp2 == 0 && temp3 == 0 && temp6 == 0 && temp9 == 0 && temp8 == 0 && temp7 == 0 && temp4 == 0) {
                    coupsjouables[i][j] = 0; // case injouable
                    continue;
                }
                coupsjouables[i][j] = temp1 + temp2 + temp3 + temp4 + temp6 + temp9 + temp8 + temp7 + 1;
            }
        }
        return coupsjouables;
    } 

    public static boolean casejouable(int[][] plateau, int x, int y, int yourteam) {
        int temp1, temp2, temp3, temp6, temp9, temp8, temp7, temp4;
        if (plateau[x][y] != 0) {
            return false;
        }
        temp1 = Tray.testdiagversbasgauche(plateau, x, y, yourteam);
        temp2 = Tray.testcolonneverslebas(plateau, x, y, yourteam);
        temp3 = Tray.testdiagversbasdroite(plateau, x, y, yourteam);
        temp6 = Tray.testligneversladroite(plateau, x, y, yourteam);
        temp9 = Tray.testdiagvershautdroite(plateau, x, y, yourteam);
        temp8 = Tray.testcolonneverslehaut(plateau, x, y, yourteam);
        temp7 = Tray.testdiagvershautgauche(plateau, x, y, yourteam);
        temp4 = Tray.testligneverslagauche(plateau, x, y, yourteam);
        if (temp1 ==  0 && temp2 == 0 && temp3 == 0 && temp6 == 0 && temp9 == 0 && temp8 == 0 && temp7 == 0 && temp4 == 0) {
            return false;
        }
        return true;
    }

    public static int[] iaPlay(int[][] plateau, int yourteam) {
        int[][] playableBoxes = test_playableBoxes(plateau, yourteam);
        int size = plateau.length;
        int buffervalue = 0;
        Random r = new Random();
        int rand;
        int bufferplace[] = new int[2];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (playableBoxes[i][j] > buffervalue) {
                    bufferplace[0] = i;
                    bufferplace[1] = j;
                    buffervalue = playableBoxes[i][j];
                }
                if (playableBoxes[i][j] == buffervalue) {
                    rand = r.nextInt();
                    if (rand == 1) {   
                        bufferplace[0] = i;
                        bufferplace[1] = j;
                        buffervalue = playableBoxes[i][j];
                    }
                }
            }
        }
        return bufferplace;
    }

    public static int[] iaPlayWithHeuristics(int[][] plateau, int yourteam) {
        int[][] playableBoxes = test_playableBoxes(plateau, yourteam);
        int size = plateau.length;
        int buffervalue = 0;
        Random r = new Random();
        int rand;
        int bufferplace[] = new int[2];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == 0 || j == 0 || i == size-1 || j == size-1) {
                    if (playableBoxes[i][j] != 0)
                        playableBoxes[i][j] = playableBoxes[i][j] + 4;
                    if (i == 0 && j == 0 && playableBoxes[i][j] != 0)
                        playableBoxes[i][j] = playableBoxes[i][j] + 30;
                    if (i == 0 && j == size-1 && playableBoxes[i][j] != 0)
                        playableBoxes[i][j] = playableBoxes[i][j] + 30;
                    if (i == size-1 && j == 0 && playableBoxes[i][j] != 0)
                        playableBoxes[i][j] = playableBoxes[i][j] + 30;
                    if (i == size-1 && j == size-1 && playableBoxes[i][j] != 0)
                        playableBoxes[i][j] = playableBoxes[i][j] + 30;
                }
                if (playableBoxes[i][j] > buffervalue) {
                    bufferplace[0] = i;
                    bufferplace[1] = j;
                    buffervalue = playableBoxes[i][j];
                }
                if (playableBoxes[i][j] == buffervalue) {
                    rand = r.nextInt();
                    if (rand == 1) {   
                        bufferplace[0] = i;
                        bufferplace[1] = j;
                        buffervalue = playableBoxes[i][j];
                    }
                }
            }
        }
        return bufferplace;
    }


    public static boolean noPlaceToPlay(int[][] plateau, int yourteam) {
        int size = plateau.length;
        boolean retour = true;
        int[][]buffer = new int[size][size];
        buffer = test_playableBoxes(plateau, yourteam);
        for (int i = 0; i < size ; i++) {
            for (int j = 0; j < size; j++) {
                if (buffer[i][j] != 0) {
                    retour = false;
                    return retour;
                }
            }
        }
        return retour;
    } 


    public static int[] convertCoordArray(String input, int n) {
        int tempX = 0;
        int tempY = 0;
        if (input.compareTo("PASS") == 0) {
            tempX = -1;
            tempY = -1;
        }
        else {
            tempX = (int) input.charAt(0)-97;
            tempY = n-1;
            if (tempX > 25) {
                System.out.println(tempX);
                System.out.println("ERROR");
                tempX = -2;
            }
            if (tempY > 25) {
                System.out.println("ERROR");
                System.out.println(tempY);
                tempY = -2;
            }

        }
        int[] output = {tempX,tempY};
        return output;
    }

    public static String convertIntoCoord(int[] coords) {
        char tempX;
        int tempY = 0;
        tempX = (char) (coords[0]+97);
        tempY = coords[1]+1;
        String output = "" + tempX + " " + tempY;
        return output;
    }
}