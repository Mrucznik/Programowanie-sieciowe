using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;

namespace Lab2
{
    class Program
    {
        /*
         * Zadanie:
         * http://rwajman.iis.p.lodz.pl/materials/ps1/Laboratoria/PS_lab_04_VS%20i%20watki.pdf
         * 
         * Wykład:
         * http://rwajman.iis.p.lodz.pl/materials/ps1/Wyklady/PS1%20-%20Wyklad%204%20-%20Serwer%20TCP.pdf
         * 
         * Dokumentacja:
         * https://msdn.microsoft.com/en-us/library/system.net.sockets.socket.listen(v=vs.110).aspx
         * 
         */

        static void Main(string[] args)
        {
            Console.WriteLine("Provide communication port: ");
            if (!int.TryParse(Console.ReadLine(), out var port))
                port = 7;


            Console.WriteLine("Creating socket... ");
            Socket socket = new Socket(
                AddressFamily.InterNetwork, //serwer działa tylko w wewnętrznej sieci
                SocketType.Stream,
                ProtocolType.Unspecified
            );

            Console.WriteLine("Binding... ");
            socket.Bind(new IPEndPoint(IPAddress.Parse("127.0.0.1"), port));
            
            Console.WriteLine("Listening... ");
            socket.Listen(5);

            int counter = 0;
            while (true)
            {
                Socket client = socket.Accept();
                Console.WriteLine("Polaczenie z {0}", client.RemoteEndPoint);

                DateTime now = DateTime.Now;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.AppendLine($"Data: {now.Day:00}/{now.Month:00}/{now.Year:0000}");
                stringBuilder.AppendLine($"Czas: {now.Hour:00}:{now.Minute:00}:{now.Second:00}");
                stringBuilder.AppendLine($"Jesteś klientem nr {counter}");

                byte[] bufor = Encoding.ASCII.GetBytes(stringBuilder.ToString());
                client.Send(bufor);
                client.Close();
            }
        }
    }
}
