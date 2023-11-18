class Life extends Vision
{
  
  int boardWidth = 200;
  int boardHeight;
  float cellWidth;
  float[][] board = new float[boardHeight][boardWidth];
  float[][] nextBoard  = new float[boardHeight][boardWidth];

  int numAlive = 0;
  
  int pattern = 0;
  int generation = 0;
  int generationMax = 10000;
  
  Life(int pattern, int generationMax)
  {
    this.pattern = pattern;
    this.generationMax = generationMax;
  }
  
  void restart()
  {
    switch(pattern)
    {
      case 0:
        crossShape();
        break;
      case 1:
        randomize();
        break;
      case 2:
        boxShape();
        break;
    }
  }
  
  void boxShape()
  {
    clearBoard();
    int x1 = (int)(boardWidth * 0.2f);
    int x2 = (int)(boardWidth * 0.8f);
    
    for (int col = x1; col < x2; col++)
    {
        board[(int)(boardHeight * 0.2f)][col] = random(255);
        board[(int)(boardHeight * 0.8f)][col] = random(255);
    }
    
    x1 = (int)(boardHeight * 0.2f);
    x2 = (int)(boardHeight * 0.8f);
    
    
    for (int row = x1; row < x2; row++)
    {
        board[row][(int)(boardWidth * 0.2f)] = random(255);
        board[row][(int)(boardWidth * 0.8f)] = random(255);
    }
    
  }

  void crossShape()
  {
    clearBoard();
    //generation = 0;
    int halfW = boardWidth / 2;
    int halfH = boardHeight / 2;  
    for (int i = 0; i < boardWidth; i ++)
    {
      board[halfH][i] = random(255);
    }
    for (int i = 0; i < boardHeight; i ++)
    {
      board[i][halfW] = random(255);
    }
  }

  

  void xShape()
  {  
    clearBoard();
    int half = boardHeight / 2;
    for (int i = 0; i < boardHeight; i ++)
    {
      board[i][i] = random(255);
      board[(boardWidth - 1) - i][i] = random(255);
    }
  }


  void clearBoard()
  {
    background(0);
    for (int row = 0; row < boardHeight; row ++)
    {
      for (int col = 0; col < boardWidth; col ++)
      {
        board[row][col] = -1;
      }
    }
    generation = 0;
  }

  void initialize()
  {
    cellWidth = width / (float) boardWidth;
    boardHeight = round(height / cellWidth);
    board = new float[boardHeight][boardWidth];
    nextBoard  = new float[boardHeight][boardWidth];
    restart();
  }
  
  void updateBoard()
  {
    numAlive = 0;
    for (int row = 0; row < boardHeight; row ++)
    {
      for (int col = 0; col < boardWidth; col ++)
      {
        int c = countAround(row, col);
        if (board[row][col] >= 0)
        {
          if (c == 2 || c == 3)
          {
            nextBoard[row][col] = board[row][col];
            numAlive ++;
          } else
          {
            nextBoard[row][col] = -1;
          }
        } else
        {
          if (c == 3)
          {
            nextBoard[row][col] = randomAround(row, col);
            numAlive ++;
          } else
          {
            nextBoard[row][col] = -1;
          }
        }
      }
    }
    float[][] temp = board;
    board = nextBoard;
    nextBoard = temp;
    generation ++;
  }

  float getElement(int row, int col)
  {
    if (row >= 0 && row < boardHeight && col >= 0 && col < boardWidth)
    {
      return board[row][col];
    } else return -1;
  }

 
  float randomAround(int row, int col)
  {
    float sum = 0;
    float xsum = 0;
    float ysum = 0;
    int ec = 0;
    for (int r = row - 1; r <= row + 1; r ++)
    {
      for (int c = col - 1; c <= col + 1; c ++)
      {

        float e = getElement(r, c);
        if (!(r == row && c == col)  && e != -1)
        {
          float angle = map(e, 0, 255, -PI, PI);
          xsum += cos(angle);
          ysum += sin(angle);       
          ec ++;
        }
      }
    }
    xsum /= 3.0f;
    ysum /= 3.0f;

    return map(atan2(ysum, xsum), -PI, PI, 0, 255);
  }


  void randomize()
  {
    //clearBoard();
    generation = 0;
    for (int row = 0; row < boardHeight; row ++) {
      for (int col = 0; col < boardWidth; col ++) {
        if (random(0, 1) < 0.5f)
        {
          board[row][col] = random(255);
        } else
        {
          board[row][col] = -1;
        }
      }
    }
  }

  void drawBoard()
  {
    colorMode(RGB);
    fill(0, 10);
    rectMode(CORNER);
    rect(0, 0, width, height);
    colorMode(HSB);
    noStroke();
    for (int row = 0; row < boardHeight; row ++) {
      for (int col = 0; col < boardWidth; col ++) {
        if (board[row][col] != -1)
        {
          fill(board[row][col], 255, 255);
          rect(col * cellWidth, row * cellWidth, cellWidth, cellWidth);
        }
      }
    }
  }

  void render()
  {    
    if (amplitude > map(mouseY, 0, height, 0.1, 0.4f))
    {
      updateBoard();
    }
    drawBoard();

    if (generation == generationMax)
    {
      restart();
    }
  }

  int countAround(int row, int col)
  {
    int count = 0;
    if (row > 0 && col > 0 && board[row-1][col-1] != -1)
    {
      count ++;
    }
    if (row > 0 && board[row -1][col] != -1)
    {
      count ++;
    }
    if (row > 0 && col < boardWidth -1 && board[row-1][col+1] != -1)
    {
      count ++;
    }
    if (col>0 && board[row][col-1] != -1)
    {
      count ++;
    }
    if (col < boardWidth - 1 && board[row][col+1] != -1)
    {
      count ++;
    }
    if (col > 0 && row < boardHeight -1 && board[row+1][col-1] != -1)
    {
      count ++;
    }
    if (row < boardHeight -1 && board[row + 1][col] != -1)
    {
      count ++;
    }
    if (row < boardHeight - 1 && col < boardWidth -1 && board[row+1][col+1] != -1) 
    {
      count ++;
    }

    return count;
  }
}
