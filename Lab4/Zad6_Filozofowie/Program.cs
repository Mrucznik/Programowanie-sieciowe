using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Lab4_Zad6_Filozofowie
{
    public class Program
    {
        static void Main(string[] args)
        {
            Fork[] forks = new Fork[5];
            Filozof[] filozofowie = new Filozof[5];

            filozofowie[0] = new Filozof(forks[0], forks[1]);
            filozofowie[1] = new Filozof(forks[1], forks[2]);
            filozofowie[2] = new Filozof(forks[2], forks[3]);
            filozofowie[3] = new Filozof(forks[3], forks[4]);
            filozofowie[4] = new Filozof(forks[4], forks[0]);

            filozofowie[0].LeftFilozof = filozofowie[4];
            filozofowie[0].RightFilozof = filozofowie[1];

            filozofowie[1].LeftFilozof = filozofowie[0];
            filozofowie[1].RightFilozof = filozofowie[2];

            filozofowie[2].LeftFilozof = filozofowie[1];
            filozofowie[2].RightFilozof = filozofowie[3];

            filozofowie[3].LeftFilozof = filozofowie[2];
            filozofowie[3].RightFilozof = filozofowie[4];

            filozofowie[4].LeftFilozof = filozofowie[3];
            filozofowie[4].RightFilozof = filozofowie[0];

            while (true)
            {
                foreach (var filozof in filozofowie)
                {
                }
            }
        }
    }
}
