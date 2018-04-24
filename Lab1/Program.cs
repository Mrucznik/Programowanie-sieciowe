using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;

namespace Programowanie_sieciowe
{
    class Program
    {
        static void Main(string[] args)
        {
            Socket socket = new Socket(
                AddressFamily.InterNetwork,
                SocketType.Stream,
                ProtocolType.Unspecified
            );
            socket.Connect(new IPEndPoint(IPAddress.Parse("127.0.0.1"), 7));
            
            Console.WriteLine("Provide request data: ");
            String data = Console.ReadLine();
            Console.WriteLine("Sending request");
            socket.Send(Encoding.ASCII.GetBytes(data));

            Console.WriteLine("Waiting for response");
            byte[] buffer = new byte[1024];
            int result = socket.Receive(buffer);
            String time = Encoding.ASCII.GetString(buffer, 0, result);
            Console.WriteLine($"RESPONSE: {time}");
        }
    }
}
