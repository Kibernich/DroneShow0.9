public enum Direction {
    FORWARD{
        @Override
        public String toString() {
            return "forward";
        }
    }, BACK{
        @Override
        public String toString() {
            return "back";
        }
    }, LEFT{
        @Override
        public String toString() {
            return "left";
        }
    }, RIGHT{
        @Override
        public String toString() {
            return "right";
        }
    }, UP{
        @Override
        public String toString() {
            return "up";
        }
    }, DOWN{
        @Override
        public String toString() {
            return "down";
        }
    };
}
