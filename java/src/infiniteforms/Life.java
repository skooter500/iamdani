package infiniteforms;

import ie.tudublin.AudioGarden;
import ie.tudublin.Poly;

public class Life extends Poly
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

  public Life(AudioGarden v,  int pattern, int generationMax, int boardSize)
  {
    super(v);
    this.boardWidth = boardSize;
    
    this.pattern = pattern;
    this.generationMax = generationMax;
    initialize();
    clearBoard();
  }

  public void makePattern()
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
      case 3:
        MakeGosperGun((int) (boardWidth * 0.4f) , (int) (boardHeight * 0.45f));
        break;
      case 4:
        MakeTumbler((int) (boardWidth * 0.5) , boardHeight /2);
        break;
      case 5:
        MakeLightWeightSpaceShip((int) (boardWidth * 0.25f) , boardHeight /2);
        break;


    }
  }
  
  public void enter()
  {
    clearBoard();
    makePattern();
  }

  public void MakeGosperGun(int x, int y)
    {
      //clearBoard();
        On(x + 23, y);
        On(x + 24, y);
        On(x + 34, y);
        On(x + 35, y);

        On(x + 22, y + 1);
        On(x + 24, y + 1);
        On(x + 34, y + 1);
        On(x + 35, y + 1);

        On(x + 0, y + 2);
        On(x + 1, y + 2);
        On(x + 9, y + 2);
        On(x + 10, y + 2);
        On(x + 22, y + 2);
        On(x + 23, y + 2);

        On(x + 0, y + 3);
        On(x + 1, y + 3);
        On(x + 8, y + 3);
        On(x + 10, y + 3);

        On(x + 8, y + 4);
        On(x + 9, y + 4);
        On(x + 16, y + 4);
        On(x + 17, y + 4);

        On(x + 16, y + 5);
        On(x + 18, y + 5);

        On(x + 16, y + 6);

        On(x + 35, y + 7);
        On(x + 36, y + 7);

        On(x + 35, y + 8);
        On(x + 37, y + 8);

        On(x + 35, y + 9);

        On(x + 24, y + 12);
        On(x + 25, y + 12);
        On(x + 26, y + 12);

        On(x + 24, y + 13);

        On(x + 25, y + 14);
    }

    public void MakeLightWeightSpaceShip(int x, int y)
    {
      //clearBoard();
        On(x + 1, y);
        On(x + 2, y);
        On(x + 3, y);
        On(x + 4, y);

        On(x, y + 1);
        On(x + 4, y + 1);

        On(x + 4, y + 2);

        On(x, y + 3);
        On(x + 3, y + 3);
    }


    public void MakeTumbler(int x, int y)
    {
      //clearBoard();
        On(x + 1, y);
        On(x + 2, y);
        On(x + 4, y);
        On(x + 5, y);

        On(x + 1, y + 1);
        On(x + 2, y + 1);
        On(x + 4, y + 1);
        On(x + 5, y + 1);

        On(x + 2, y + 2);
        On(x + 4, y + 2);

        On(x, y + 3);
        On(x + 2, y + 3);
        On(x + 4, y + 3);
        On(x + 6, y + 3);

        On(x, y + 4);
        On(x + 2, y + 4);
        On(x + 4, y + 4);
        On(x + 6, y + 4);

        On(x, y + 5);
        On(x + 1, y + 5);
        On(x + 5, y + 5);
        On(x + 6, y + 5);

    }

    public void  on (int y, int x)
    {
      board[y][x] = v.random(255);
    }

    public void MakeGlider(int x, int y)
    {
      //clearBoard();
        on(y, x + 1);
        on(y + 1, x + 2) ;
        on(y + 2, x);
        on(y + 2, x + 1) ;
        on(y + 2, x + 2) ;
    }
  
  void boxShape()
  {
    //clearBoard();
    int x1 = (int)(boardWidth * 0.2f);
    int x2 = (int)(boardWidth * 0.8f);
    
    for (int col = x1; col < x2; col++)
    {
        board[(int)(boardHeight * 0.2f)][col] = v.random(255);
        board[(int)(boardHeight * 0.8f)][col] = v.random(255);
    }
    
    x1 = (int)(boardHeight * 0.2f);
    x2 = (int)(boardHeight * 0.8f);
    
    
    for (int row = x1; row < x2; row++)
    {
        board[row][(int)(boardWidth * 0.2f)] = v.random(255);
        board[row][(int)(boardWidth * 0.8f)] = v.random(255);
    }
    
  }

  void crossShape()
  {
    //clearBoard();
    //generation = 0;
    int halfW = boardWidth / 2;
    int halfH = boardHeight / 2;  
    for (int i = 0; i < boardWidth; i ++)
    {
      board[halfH][i] = v.random(255);
    }
    for (int i = 0; i < boardHeight; i ++)
    {
      board[i][halfW] = v.random(255);
    }
  }

  

  void xShape()
  {  
    //clearBoard();
    int half = boardHeight / 2;
    for (int i = 0; i < boardHeight; i ++)
    {
      board[i][i] = v.random(255);
      board[(boardWidth - 1) - i][i] = v.random(255);
    }
  }


  void clearBoard()
  {
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
    cellWidth = v.width * 2.0f / (float) boardWidth;
    boardHeight = v.round(v.height * 2.0f / cellWidth);
    board = new float[boardHeight][boardWidth];
    nextBoard  = new float[boardHeight][boardWidth];
    enter();
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
    v.println("Generation: " + generation + " ALIVE: " + numAlive);
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
          float angle = v.map(e, 0, 255, -v.PI, v.PI);
          xsum += v.cos(angle);
          ysum += v.sin(angle);       
          ec ++;
        }
      }
    }
    xsum /= 3.0f;
    ysum /= 3.0f;

    return v.map(v.atan2(ysum, xsum), -v.PI, v.PI, 0, 255);
  }

  public void On(int x, int y)
  {
      if ((x >= 0) && (x < boardWidth) && (y >= 0) && (y < boardHeight))
      {
          board[y][x] = v.random(255);
      }
  }

  public void Off(int x, int y)
  {
      if ((x >= 0) && (x < boardWidth) && (y >= 0) && (y < boardHeight))
      {
          board[y][x] = 0;
      }
  }


  void randomize()
  {
    //clearBoard();
    generation = 0;
    for (int row = 0; row < boardHeight; row ++) {
      for (int col = 0; col < boardWidth; col ++) {
        if (v.random(0, 1) < 0.5f)
        {
          board[row][col] = v.random(255);
        } else
        {
          board[row][col] = -1;
        }
      }
    }
  }

  void drawBoard()
  {
    
    for (int row = 0; row < boardHeight; row ++) {
      for (int col = 0; col < boardWidth; col ++) {
        if (board[row][col] != -1)
        {
            float newC = v.hueShift(board[row][col] + ((v.pit + v.yaw) * 10000.0f));
            v.fill(newC, 255, 255, v.alp);
            v.strokeWeight(1);
            v.stroke(v.hueShift(90), 255, 255, v.alp);
            // v.fill(v.hueShift(newC),(255+v.frameCount)%255,(255+v.frameCount)%255, v.alp);
                
            // v.stroke(v.hueShift(newC + 127), 255, 255, v.alp);
            // v.strokeWeight(2);
            v.pushMatrix();
            v.translate(col * cellWidth, row * cellWidth, 0);
            v.box(cellWidth);
            v.popMatrix();
          //rect(col * cellWidth, row * cellWidth, cellWidth, cellWidth);
          //v.rect(col * cellWidth, row * cellWidth, cellWidth, cellWidth);
        }
      }
    }
    v.noLights();
  }

  public void render()
  {    

    v.camera(0, 0, -1000, 0, 0, 0f, 0f, 0.001f, 0f);
    v.lights();
    v.translate(0, 0, 1000);
    v.rotateX(v.pit);
    v.rotateY(v.yaw - 0.6f);
    
    v.translate(-(boardWidth * cellWidth) / 2, -(boardHeight * cellWidth) / 2, 0);
    
    int toPass = (int) v.map(v.spe, 0, 3, 120, 2);  

    if (v.frameCount % toPass == 0)
    {
      updateBoard();
    }
    

    drawBoard();

    if (generation == generationMax)
    {
      enter();
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
