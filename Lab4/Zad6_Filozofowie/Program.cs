using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace Lab4_Zad6_Filozofowie
{
    public class Program
    {
        static void Main(string[] args)
        {
            int philosophersNumber = 5;

            Fork[] forks = new Fork[philosophersNumber];

            for (int i = 0; i < philosophersNumber; i++)
            {
                forks[i] = new Fork();
            }

            Filozof[] philozophers = new Filozof[philosophersNumber];
            for (int i = 0; i < philosophersNumber; i++)
            {
                philozophers[i] = new Filozof("kek", forks[i], forks[(i+1)%philosophersNumber]);
            }

            Console.WriteLine("Uczta rozpoczęta");
            
            for (int i = 0; i < philosophersNumber; i++)
            {
                var idx = i;
                new Thread(() =>
                {
                    philozophers[idx].Dine();
                }).Start();
            }

            Console.WriteLine("Uczta skończona");
        }
    }
}
