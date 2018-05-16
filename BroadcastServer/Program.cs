using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;

namespace BroadcastServer
{
    class Program
    {
        static void Main(string[] args)
        {
            UdpClient udpclient = new UdpClient();

            udpclient.EnableBroadcast = true;

            IPEndPoint remoteep = new IPEndPoint(IPAddress.Broadcast, 2222);

            Console.WriteLine("Press ENTER to start sending messages");
            Console.ReadLine();

            for (int i = 0; i <= 8000; i++)
            {
                var buffer = Encoding.Unicode.GetBytes(i.ToString());
                udpclient.Send(buffer, buffer.Length, remoteep);
                Console.WriteLine("Sent " + i);
            }

            Console.WriteLine("All Done! Press ENTER to quit.");
            Console.ReadLine();
        }
    }
}
