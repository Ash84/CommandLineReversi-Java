public class MinMax {

    public static int[] coupMinMax(int[][] tray, int maxColor) {

        int[][] traytotest = Tray.copy_tray(tray);
        int minColor = 1;
        if (maxColor == 1) {
            minColor = 2;
        }
        int[][] playableBoxes = ia.test_playableBoxes(traytotest, maxColor);
        int bufferplace[] = new int[2]; // x, y.
        boolean maxPlaying = false;
        int size = traytotest.length;
        double testValue;
        int level = 0;
        double alpha = Double.NEGATIVE_INFINITY;
        double beta = Double.POSITIVE_INFINITY;
        double buffer = Double.POSITIVE_INFINITY;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (playableBoxes[i][j] > 0) {
                    testValue = minmax(traytotest, level, alpha, beta, i, j, minColor, maxColor, maxPlaying);
                    if (testValue < buffer) {
                        buffer = testValue;
                        bufferplace[0] = i;
                        bufferplace[1] = j;
                    }
                }
            }
        }
        return bufferplace;
    }

    public static double minmax(int[][] tray, int level, double alpha, double beta, int coupX, int coupY, int minColor, int maxColor, boolean maxPlaying) {

        
        // Minimizer player.
        if (maxPlaying == false) {
            double buffer = Double.POSITIVE_INFINITY;
            int[][] newtray = Tray.reverse_tray(tray, coupX, coupY, maxColor);
            // Returns score for previous max play.
            if (level > 6 || ia.noPlaceToPlay(newtray, maxColor)) {
                return scoreTestMinmax(newtray, maxColor);
            } 

        //  Optimize worse best play for min.
            int[][] playableBoxes = ia.test_playableBoxes(tray, minColor);
            int size = tray.length;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (playableBoxes[i][j] > 0) {
                        buffer = minmax(newtray, level+1, alpha, beta, i, j, minColor, maxColor, true);
                        if (buffer < beta)
                            beta = buffer;
                        if (alpha >= beta)
                            break;
                    }
                }
            }
        return beta;
        }
        // Maximizer player.
        else {
            double buffer = Double.NEGATIVE_INFINITY;
            int[][] newtray = Tray.reverse_tray(tray, coupX, coupY, minColor);
            // Returns score for previous min play.
            if (level > 6 || ia.noPlaceToPlay(newtray, minColor)) {
                return scoreTestMinmax(newtray, minColor);
            } 
            // Calcul meilleur coup pour max
            int[][] playableBoxes = ia.test_playableBoxes(tray, maxColor);
            int size = tray.length;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (playableBoxes[i][j] > 0) {
                        buffer = minmax(newtray, level+1, alpha, beta, i, j, minColor, maxColor, false);
                        if (buffer > alpha) {
                            alpha = buffer;
                        }
                        if (alpha >= beta)
                            break;
                    }
                }
            }
        return alpha;
        }
    }


    public static double scoreTestMinmax(int[][] tray, int maxColor) {
        int minColor = 1;
        if (maxColor == 1)
            minColor = 2;
        if (ia.noPlaceToPlay(tray, minColor) && ia.noPlaceToPlay(tray, maxColor)) {
            int[] scoresBlackWhite = game.scores(tray);
            if ((maxColor == 1) && (scoresBlackWhite[0] - scoresBlackWhite [1] > 0)) {
                return Double.POSITIVE_INFINITY;
            }
            if ((minColor == 1) && (scoresBlackWhite[0] - scoresBlackWhite [1]) > 0) {
                return Double.NEGATIVE_INFINITY;
            }
        }

        int[][] vectorForce =  {{20,-3, 11, 8, 8,11,-3, 20},
                                {-3,-7, -4, 1, 1,-4,-7, -3},
                                {11,-4,  2, 2, 2, 2,-4, 11},
                                { 8, 1,  2,-3,-3, 2, 1,  8},
                                { 8, 1,  2,-3,-3, 2, 1,  8},
                                {11,-4,  2, 2, 2, 2,-4, 11},
                                {-3,-7, -4, 1, 1,-4,-7, -3},
                                {20,-3, 11, 8, 8,11,-3, 20}};
        int size = tray.length;
        int maxPlayerCoins = 0;
        int minPlayerCoins = 0;
        int maxFrontCoins = 0;
        int minFrontCoins = 0;
        int maxPlayerMoves = 0;
        int minPlayerMoves = 0;
        int heurisMobility = 0;
        int maxCorners = 0;
        int minCorners = 0;
        int heurisCorners = 0;
        int heurisCoins = 0;
        int heurisFrontCoins = 0;
        int vFCoins = 0;
        int testsX[] = {-1, -1, 0, 1, 1, 1, 0, -1}; 
        int testsY[] = {0, 1, 1, 1, 0, -1, -1, -1};
        int[][] maxPlayableBoxes = ia.test_playableBoxes(tray, maxColor);
        int[][] minPlayableBoxes = ia.test_playableBoxes(tray, minColor);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (minPlayableBoxes[i][j] != 0) 
                    minPlayerMoves++;
                if (maxPlayableBoxes[i][j] != 0)
                    maxPlayerMoves++;
                    
                if (tray[i][j] == minColor) {
                    minPlayerCoins++;
                    vFCoins -= vectorForce[i][j];
                }
                if (tray[i][j] == maxColor) {
                    maxPlayerCoins++;
                    vFCoins += vectorForce[i][j];
                }
                if(tray[i][j] != 0)   {
                    for (int k = 0; k < 8; k++)  {
                        int x = i + testsX[k]; 
                        int y = j + testsY[k];
                        if(x >= 0 && x < 8 && y >= 0 && y < 8 && tray[x][y] == 0) {
                            if(tray[i][j] == maxColor)
                                maxFrontCoins++;
                            else 
                                minFrontCoins++;
                        }
                    }
                }
            }
        }

        // Coin Parity heuristics.
        if(maxPlayerCoins > minPlayerCoins && (maxFrontCoins + minFrontCoins) != 0)
            heurisCoins = (100 * maxPlayerCoins)/(maxPlayerCoins + minPlayerCoins);
        else if(maxPlayerCoins < minPlayerCoins)
            heurisCoins = -(100 * minPlayerCoins)/(maxPlayerCoins + minPlayerCoins);
        else heurisCoins = 0;

        if(maxFrontCoins > minFrontCoins && (maxFrontCoins + minFrontCoins) != 0)
            heurisFrontCoins = -(100 * maxFrontCoins)/(maxFrontCoins + minFrontCoins);
        else if(maxFrontCoins < minFrontCoins)
            heurisFrontCoins = (100 * minFrontCoins)/(maxFrontCoins + minFrontCoins);
        else heurisFrontCoins = 0;

        // Corners Heuristics.
        if (0 != tray[0][0] || 0 != tray[0][size-1] || 0 != tray[size-1][0] || 0 != tray[size-1][size-1]) {
            if (tray[0][0] == maxColor)
                maxCorners++;
            else if (tray[0][0] == minColor)
                minCorners++;
            if (tray[0][size-1] == maxColor)
                maxCorners++;
            else if (tray[0][size-1] == minColor)
                minCorners++;
            if (tray[size-1][0] == maxColor)
                maxCorners++;
            else if (tray[size-1][0] == minColor)
                minCorners++;
            if (tray[size-1][size-1] == maxColor)
                maxCorners++;
            else if (tray[size-1][size-1] == minColor)
                minCorners++;
        }
        heurisCorners = 25 * (maxCorners - minCorners);

        	
        if(tray[0][0] == 0) {
            if(tray[0][1] == maxColor) 
                maxCorners++;
            else if(tray[0][1] == minColor) 
                minCorners++;
            if(tray[1][1] == maxColor) 
                maxCorners++;
            else if(tray[1][1] == minColor) 
                minCorners++;
            if(tray[1][0] == maxColor) 
                maxCorners++;
            else if(tray[1][0] == minColor) 
                minCorners++;
        }
        if(tray[0][size-1] == 0) {
            if(tray[0][size-2] == maxColor) 
                maxCorners++;
            else if(tray[0][size-2] == minColor) 
                minCorners++;
            if(tray[1][size-2] == maxColor) 
                maxCorners++;
            else if(tray[1][size-2] == minColor) 
                minCorners++;
            if(tray[1][size-1] == maxColor) 
                maxCorners++;
            else if(tray[1][size-1] == minColor) 
                minCorners++;
        }
        if(tray[size-1][0] == 0) {
            if(tray[size-1][1] == maxColor) 
                maxCorners++;
            else if(tray[size-1][1] == minColor) 
                minCorners++;
            if(tray[size-2][1] == maxColor) 
                maxCorners++;
            else if(tray[size-2][1] == minColor) 
                minCorners++;
            if(tray[size-2][0] == maxColor) 
                maxCorners++;
            else if(tray[size-2][0] == minColor) 
                minCorners++;
        }
        if(tray[size-1][size-1] == 0) {
            if(tray[size-2][size-1] == maxColor) 
                maxCorners++;
            else if(tray[size-2][size-1] == minColor) 
                minCorners++;
            if(tray[size-2][size-2] == maxColor) 
                maxCorners++;
            else if(tray[size-2][size-2] == minColor) 
                minCorners++;
            if(tray[size-1][size-2] == maxColor) 
                maxCorners++;
            else if(tray[size-1][size-2] == minColor) 
                minCorners++;
        }
        double heurisCloseCorners = -12.5 * (maxCorners - minCorners);
        
        // Mobility Heuristics.
        if ((maxPlayerMoves > minPlayerMoves) && (maxPlayerMoves - minPlayerMoves) != 0)
            heurisMobility = 100 * (maxPlayerMoves - minPlayerMoves / (maxPlayerMoves - minPlayerMoves));
        else if ((minPlayerMoves > maxPlayerMoves) && (maxPlayerMoves - minPlayerMoves) != 0)
            heurisMobility = - (100 * (maxPlayerMoves - minPlayerMoves / (maxPlayerMoves - minPlayerMoves)));
        else 
            heurisMobility = 0;
    

        // Final Score weighted.
        double scorew = (10 * heurisCoins) + (10 * vFCoins) + (382.026 * heurisCloseCorners) + (74.396 * heurisFrontCoins) + (801.724 * heurisCorners) + (78.922 * heurisMobility);
        return scorew;
    }
}