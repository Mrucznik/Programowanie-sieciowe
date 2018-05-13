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
            {
                port = 7;
            }

            try
            {
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

                while (true)
                {
                    Socket client = socket.Accept();
                    Console.WriteLine("Connected with {0}", client.RemoteEndPoint);

                    byte[] buffer = new byte[1024];
                    client.Receive(buffer);

                    var recivedString = Encoding.ASCII.GetString(buffer);
                    var sendString = recivedString.Substring(0, recivedString.IndexOf('\n'));

                    Console.WriteLine($"Recived: {recivedString}");
                    Console.WriteLine($"Sending: {sendString}");
                    client.Send(Encoding.ASCII.GetBytes(sendString));

                    Console.WriteLine("Data sent, closing connection");
                    client.Close();
                }
            }
            catch (SocketException e)
            {
                Console.WriteLine(e.Message);
            }
            
        }
    }
}
