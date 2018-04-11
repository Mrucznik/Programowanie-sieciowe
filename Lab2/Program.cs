using System;
using System.Collections.Generic;
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
            Socket socket = new Socket(
                AddressFamily.InterNetwork, //serwer działa tylko w wewnętrznej sieci
                SocketType.Stream,
                ProtocolType.Unspecified
            );

            socket.Bind(new IPEndPoint(IPAddress.Parse("127.0.0.1"), 3301);
        }
    }
}
