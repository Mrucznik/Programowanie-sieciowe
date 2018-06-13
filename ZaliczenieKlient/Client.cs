using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading;
using System.Threading.Tasks;

namespace ZaliczenieKlient
{
    class Client
    {
        static User user;
        static UdpClient UDPsender;
        static UdpClient UDPreciver;

        static IPAddress multicastaddress;
        static IPEndPoint localEp;
        static IPEndPoint remoteEp;

        static Sender sender;
        static Reciver reciver;


        static void Main(string[] args)
        {
            user = new User();
            UDPsender = new UdpClient { ExclusiveAddressUse = false };
            UDPreciver = new UdpClient() { ExclusiveAddressUse = false };

            multicastaddress = IPAddress.Parse("239.0.0.222");
            localEp = new IPEndPoint(IPAddress.Any, 2222);
            remoteEp = new IPEndPoint(multicastaddress, 2222);

            UDPreciver.Client.SetSocketOption(SocketOptionLevel.Socket, SocketOptionName.ReuseAddress, true);
            UDPreciver.Client.Bind(localEp);
            UDPreciver.JoinMulticastGroup(multicastaddress);
            UDPsender.JoinMulticastGroup(multicastaddress);
            UDPreciver.MulticastLoopback = false;

            sender = new Sender(UDPsender, remoteEp);

            reciver = new Reciver(UDPreciver, localEp);
            Thread listenThread = new Thread(() =>
            {
                while (true)
                {
                    Byte[] data = UDPreciver.Receive(ref localEp);
                    string message = Encoding.Unicode.GetString(data);
                    Console.WriteLine("RECIVED: " + message);

                    int messageType = MessageManager.GetMessageType(message);
                    
                    if(messageType == MessageManager.NICK_MESSAGE) //validate nick
                    {
                        Match messageMatch = MessageManager.GetMessageData(messageType, message);
                        if (messageMatch.Groups[1].ToString() == user.Nick)
                        {
                            sender.SendMessage(MessageManager.GetNickIsUsedMessage(user.Nick));
                            Console.WriteLine("zajety nick " + messageMatch.Groups[1]);
                        }
                        else
                        {
                            Console.WriteLine("dobry nick " + messageMatch.Groups[1]);
                        }
                    }
                }
            });
            listenThread.Start();

            SetNick();
        }

        public static void SetNick()
        {
            Console.Write("Podaj nick:");
            var nick = Console.ReadLine();
            sender.SendMessage(MessageManager.GetSetNickMessage(nick));
            user.Nick = nick;
        }
    }
}
