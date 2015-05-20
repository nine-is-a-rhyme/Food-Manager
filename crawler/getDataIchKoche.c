//
//------------------------------------------------------------------------------
// getData.c
//
// Program to extract ingreidients out of html files. 
// Files must be named as "x" where x is a number.
//
// Authors: Matthias Eder 1331015, 
// 
// Latest Changes: 06.05.2014
//------------------------------------------------------------------------------
//

//------------------------------------------------------------------------------
// includes
//------------------------------------------------------------------------------
#include <stdlib.h>
#include <stdio.h>
#include <string.h>


//------------------------------------------------------------------------------
// defines
//------------------------------------------------------------------------------

// the name of the reciepe
#define TITLE "<h1 itemprop=\"name\" class=\"page_title\">"
#define TITLE_END "</h1>"

// the number of the ingredients
#define AMOUNT "<span class=\"amount\">"
#define AMOUNT_END "</span>"

// the name of the ingredient
#define INGREDIENT "<span class=\"name\">"
#define INGREDIENT_END "</span>"

// The description of the meal
#define DESCRIPTION "<div itemprop=\"recipeInstructions\" class=\"description\">"
#define DESCRIPTION_END "</div>"

#define DURATION "<p class=\"duration\">"
#define DURATION_END "</a>"

#define IMAGE "<img itemprop=\"image\" src=\""
#define IMAGE_END "\" "

#define NAME "{\"Rezeptname\":\""
#define URL "\"Bild\":\""
#define DESC "\"Zubereitung\":\""
#define TIME "\"Kochdauer\":\""
#define NUM "{\"Menge\":\""
#define INGNAME "\"Zutat\":\""


// the directory where the files are stored
char* directory = "files/";

// the filetype. Eg. ".txt"
char* file_type = "";

#define NO_ERROR 0
#define MEM_ERROR 1
#define PARAM_ERROR 2
#define NOT_FOUND 3
#define FILE_ERROR 4
#define NUM_ERROR 5
#define DIR_ERROR 6


#define TRUE 1
#define FALSE 0

typedef signed int error;

//------------------------------------------------------------------------------
// prototypes
//------------------------------------------------------------------------------
error printError(error error_code, char* filename);
error editDoc(char* save_file, char* num_of_files);
error writeComment(FILE* save, char* line, char* search, char* end);
int checkLine(char* line, char* search);
int getInteger(char* substring);
void getString(char* string, int number);

//------------------------------------------------------------------------------
///
/// The main function.
/// calls the function of the programs. Reads the file and saves it into a new 
/// file
//
/// @param argc the number of arguments
/// @param argv is the string with the arguments
//
/// @return error or succes
//
int main(int argc, char *argv[])
{
  if (argc != 3)
  {
    return printError(PARAM_ERROR, argv[0]);
  }
  


  error check = editDoc(argv[1], argv[2]);
  
  return printError(check, argv[0]);
}

//------------------------------------------------------------------------------
///
/// printfError.
/// prints the message to the recieved Error Code
//
/// @param error_code is the type of error
/// @param filename is the name of the file
//
/// @return error or succes
//
error printError(error error_code, char* filename)
{
  switch (error_code)
  {
    case PARAM_ERROR:
    printf("usage: %s <output-file> <num-of-files>\n", filename);
    return PARAM_ERROR;
    
    case MEM_ERROR:
    printf("error: out of memory\n");
    return MEM_ERROR;
    
    case NOT_FOUND:
    printf("error: file not found\n");
    return NOT_FOUND;
    
    case FILE_ERROR:
    printf("error: could not read file\n");
    return FILE_ERROR;

    case NUM_ERROR:
    printf("error: invalid <num-of-files>\n");
    return NUM_ERROR;

    case DIR_ERROR:
    printf("error: no directory named '%s' ", directory);
    printf("or wrong filetype '%s'\n", file_type);
    return DIR_ERROR;
    
  }
  printf("Ingredients stored\n");
  return NO_ERROR;
}

//------------------------------------------------------------------------------
///
/// editDoc.
/// reads out every line of a file and saves them into another file
//
/// @param save_file is the file where the data is stored in
/// @param num_of_files is the number of files
//
/// @return error or succes
//
error editDoc(char* save_file, char* num_of_files)
{

  FILE* save = fopen(save_file, "w");
  if (save == NULL)
  {
    return NOT_FOUND;
  }
  
  // write the first line
  char* save_line_1 = "{\"Rezepte\":[";
  char* save_line_2 = "\"Zutaten\":[";
  char* save_end = "],";
  char* save_end2 = "]}";
  fwrite(save_line_1, sizeof(char), strlen(save_line_1), save);
  //fwrite(save_line_2, sizeof(char), strlen(save_line_2), save);

  int num_files = getInteger(num_of_files);
  if (num_files == -1)
  {
    return NUM_ERROR;
  }
  // get a new string for reading every single line
  char* line;
  int string_size = 8;
  int counter = 0;
  int character;

  line = (char*) malloc(sizeof(char) * string_size);
  if (line == NULL)
  {
    fclose(save);
    return MEM_ERROR;
  }


  int dir_check = -1; // offset by one, therefor -1
  int actual_file = 0;
for (; actual_file <= num_files; actual_file++)
{

  char open_file[100];
  sprintf(open_file, "./%s%d%s", directory, actual_file, file_type);

  FILE* read = fopen(open_file, "r");
  if (read == NULL)
  {
    dir_check++;
    continue;
  }

  char reciepe_no[100];
  sprintf(reciepe_no, "%d", actual_file);
  

  // checks if we are writing the description
  int running_description = FALSE;
  int check = FALSE;
  int first_ing = TRUE;
  int first_img = TRUE;
  //go through read file and read the single lines
  // if it's a /// comment save the line into the save-file
  while (TRUE)
  {
    character = fgetc(read);
    if (ferror(read))
    {
      free(line);
      fclose(read);
      fclose(save);
      return FILE_ERROR;
    }
    
    // check if the EOF or the End of line is reached
    if (character == EOF || character == '\n')
    {
      line[counter] = '\0';
      
// search the ingredients
      if (checkLine(line, IMAGE) && checkLine(line, IMAGE_END) && first_img == TRUE)
      {
        check = TRUE;
        first_img = FALSE;
        fwrite(URL, sizeof(char), strlen(URL), save);

        writeComment(save, line, IMAGE, IMAGE_END);

        fputc('\"', save);
        fputc(',', save);


      }
      if (checkLine(line, DURATION) && checkLine(line, DURATION_END))
      {
        fwrite(TIME, sizeof(char), strlen(TIME), save);
        writeComment(save, line, DURATION, DURATION_END);
        fputc('\"', save);
        fputc(',', save);
        fwrite(save_line_2, sizeof(char), strlen(save_line_2), save);


      }
      if (checkLine(line, TITLE) && checkLine(line, TITLE_END))
      {
        check = TRUE;
        fputc('\n', save);

        if (actual_file != 0)
        {
          fputc(',', save);
        }
        fwrite(NAME, sizeof(char), strlen(NAME), save);
        writeComment(save, line, TITLE, TITLE_END);
        fputc('\"', save);
        fputc(',', save);
      }

      if (checkLine(line, AMOUNT) && check == TRUE)
      { 
        if (first_ing)
        {
          first_ing = FALSE;
        }
        else
        {
          fputc(',', save);
        }
        fwrite(NUM, sizeof(char), strlen(NUM), save);
        //fputc(';', save_ingredients);
        writeComment(save, line, AMOUNT, AMOUNT_END);
        fputc('\"', save);
        fputc(',', save);
      }

      if (checkLine(line, INGREDIENT) && check == TRUE)
      {
        //fputc(' ', save_ingredients);
        fwrite(INGNAME, sizeof(char), strlen(INGNAME), save);
        writeComment(save, line, INGREDIENT, INGREDIENT_END);
        fputc('\"', save);
        //
        fputc('}', save);
      }

      // search the ingredients
      if ((checkLine(line, DESCRIPTION)  && check == TRUE) 
        || (running_description == TRUE  && check == TRUE))
      {
        if (checkLine(line, DESCRIPTION))
        {
          fwrite(save_end, sizeof(char), strlen(save_end), save);
          fwrite(DESC, sizeof(char), strlen(DESC), save);
          //fputc(';', save);
        }
        int check = writeComment(save, line, DESCRIPTION, DESCRIPTION_END);
        running_description = TRUE;

        if (check != NO_ERROR)
        {
          running_description = FALSE;
          fputc('\"', save);
          fputc('}', save);
          break;
        }
      }

      // if it's the End of file, leave the while-loop
      if (character == EOF && check == TRUE)
      {
        //fputc('\n', save);
        break;
      }
      else if (character == EOF)
      {
        break;
      }

      counter = 0;
      continue;      
    
    }
    
    line[counter] = character;
    counter++;
    
    //check if there is enough memory for the sring
    if (counter == string_size)
    {
      string_size *= 2;
      char* tmp = (char*) realloc(line, sizeof(char) * string_size);
      if (tmp == NULL)
      {
        free(line);
        fclose(read);
        fclose(save);
        return MEM_ERROR;
      }
      
      line = tmp;
    }
  }
  fclose(read);
}
  if (dir_check == num_files)
  {
    fclose(save);
    free(line);
    return DIR_ERROR;
  }
  fwrite(save_end2, sizeof(char), strlen(save_end2), save);

  fclose(save);
  free(line);
  return NO_ERROR;
}


//------------------------------------------------------------------------------
///
/// writeComment.
/// writes the comment-line into the save file
//
/// @param save is the file where the comments are stored in
/// @param line is the comment-line
/// @param search ist the begin of the ingredient string
/// @param end is the end of the ingredient string
//
error writeComment(FILE* save, char* line, char* search, char* end)
{


  int counter = 0;
  int bracket = FALSE; // set to TRUE if ther is a "<" bracket
  char* start = strstr(line, search);
  char* until;
  int space = FALSE;

  if (start != NULL)
  {
    until = strstr(start + strlen(search), end);
  }
  else
  {
    until = strstr(line, end);
  }
  if (start != NULL && until != NULL)
  {
    start += strlen(search);

    for (; counter < (strlen(start) - strlen(until)); counter++ )
    { 
      if (start[counter] == '<' || bracket == TRUE)
      {
        bracket = TRUE;
        if (start[counter] == '>')
        {
          bracket = FALSE;
        }
        continue;
      }
      if (start[counter] == '\n' || start[counter] == '\r')
      {
        fputc(' ', save);
        continue;
      }
      if (start[counter] == '\"' || start[counter] == '\t')
      {
          continue;
      }
      if ((start[counter] < 33 || start[counter] > 126) && start[counter] != ' ')
      {
          continue;
      }


      if (start[counter] == ' ')
      {
        if (space == FALSE)
          fputc(start[counter], save);
        space = TRUE;
      }
      else
      {
        fputc(start[counter], save);
        space = FALSE;
      }

    }
    return -1;
  }
  else if (start == NULL && until == NULL)
  {
    for (; counter < strlen(line); counter++ )
    {

      if (line[counter] == '<' || bracket == TRUE)
      {
        bracket = TRUE;
        if (line[counter] == '>')
        {
          bracket = FALSE;
        }
        continue;
      }
      if (line[counter] == '\n' || line[counter] == '\r')
      {
        fputc(' ', save);
        continue;
      }
      if (line[counter] == '\"' || line[counter] == '\t')
      {
          continue;
      }
      if ((line[counter] < 33 || line[counter] > 126) && line[counter] != ' ')
      {
          continue;
      }

      if (line[counter] == ' ')
      {
        if (space == FALSE)
          fputc(line[counter], save);
        space = TRUE;
      }
      else
      {
        fputc(line[counter], save);
        space = FALSE;
      }
    }
  }
  else if (start == NULL && until != NULL)
  {
    for (; counter < (strlen(line) - strlen(until)); counter++ )
    { 
      if (line[counter] == '<' || bracket == TRUE)
      {
        bracket = TRUE;
        if (line[counter] == '>')
        {
          bracket = FALSE;
        }
        continue;
      }
      if (line[counter] == '\n' || line[counter] == '\r')
      {
        fputc(' ', save);
        continue;
      }
      if (line[counter] == '\"' || line[counter] == '\t')
      {
          continue;
      }
      if ((line[counter] < 33 || line[counter] > 126) && line[counter] != ' ')
      {
          continue;
      }

      if (line[counter] == ' ')
      {
        if (space == FALSE)
          fputc(line[counter], save);
        space = TRUE;
      }
      else
      {
        fputc(line[counter], save);
        space = FALSE;
      }    }
    return -1;
  }
  else if (start != NULL && until == NULL)
  {

    start += strlen(search);

    for (; counter < strlen(start); counter++)
    { 
      if (start[counter] == '<' || bracket == TRUE)
      {
        bracket = TRUE;
        if (start[counter] == '>')
        {
          bracket = FALSE;
        }
        continue;
      }
      if (start[counter] == '\n' || start[counter] == '\r')
      {
        fputc(' ', save);
        continue;
      }
      if (start[counter] == '\"' || start[counter] == '\t')
      {
          continue;
      }
      if ((start[counter] < 33 || start[counter] > 126) && start[counter] != ' ')
      {
          continue;
      }

      if (start[counter] == ' ')
      {
        if (space == FALSE)
          fputc(start[counter], save);
        space = TRUE;
      }
      else
      {
        fputc(start[counter], save);
        space = FALSE;
      }    }
  }

  
  return NO_ERROR;
}

//------------------------------------------------------------------------------
///
/// checkLine.
/// writes the comment-line into the save file
//
/// @param line is the comment-line
/// @param search ist the string which should be searched
//
/// @return TRUE or FALSE
//
int checkLine(char* line, char* search)
{
  char* found = strstr(line, search);

  if (found == NULL)
  {
    return FALSE;
  }

  return TRUE;
}



//-----------------------------------------------------------------------------
///
/// getInteger.
/// makes out of a given character number an integer
///
/// @param substring is the number in char format
///
/// @return the integer
//------------------------------------------------------------------------------
int getInteger(char* substring)
{
  int length = strlen(substring);
  
  int counter = 0;
  int integer = 0;
  
  
  for (; counter < length; counter++)
  {
    if (substring[counter] < '0' || substring[counter] > '9')
    {
      return -1;
    }
    integer += (substring[counter] - '0');
    if (counter != (length - 1))
    {
      integer *= 10;
    }
  }
  
  
  return integer;
}


