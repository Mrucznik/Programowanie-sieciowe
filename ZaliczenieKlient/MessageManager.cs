using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;

namespace ZaliczenieKlient
{
    static class MessageManager
    {
        public static int UNKNOWN_MESSAGE = 0;
        public static int NICK_BUSSY_MESSAGE = 1;
        public static int NICK_MESSAGE = 2;
        
        private static Dictionary<int, string> messages = new Dictionary<int, string>();

        public static string GetSetNickMessage(string nick) => $"NICK {nick}";
        public static string GetNickIsUsedMessage(string nick) => $"NICK {nick} BUSY";

        static MessageManager()
        {
            messages.Add(NICK_BUSSY_MESSAGE, "NICK (.*) BUSY");
            messages.Add(NICK_MESSAGE, "NICK (.*)");
        }

        public static int GetMessageType(string message)
        {
            foreach(var m in messages)
            {
                Regex regexp = new Regex(m.Value);
                if (regexp.Match(message).Success)
                    return m.Key;
            }
            return UNKNOWN_MESSAGE;
        }

        public static Match GetMessageData(int messageType, string message)
        {
            Regex regexp = new Regex(messages[messageType]);
            return regexp.Match(message);
        }
    }
}
