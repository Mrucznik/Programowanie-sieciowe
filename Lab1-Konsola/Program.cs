using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace Lab1_Konsola
{
    class Program
    {
        static void Main(string[] args)
        {
            int i = 1;
            double sum = 0;
            Console.SetWindowSize(Console.LargestWindowWidth/2, Console.LargestWindowHeight/2);

            while (!Console.KeyAvailable)
            {
                double result = Sn(i);

                Random rand = new Random();
                Console.BackgroundColor = (ConsoleColor)(rand.Next(Enum.GetNames(typeof(ConsoleColor)).Length));
                Console.ForegroundColor = (ConsoleColor)(rand.Next(Enum.GetNames(typeof(ConsoleColor)).Length));
                Console.SetCursorPosition(0, 0);
                Console.WriteLine($"{i}\t{result}");
                Console.SetCursorPosition(80, 10);
                Console.WriteLine($"SUM={sum}");
                Console.ResetColor();
                
                sum += result;
                i += 1;
            }
        }

        private static double Sn(int n)
        {
            return Math.Pow(-1, n - 1) / (2*n - 1);
        }
    }
}
