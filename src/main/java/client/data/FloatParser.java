package client.data;

import java.awt.geom.Path2D.Float;

public class FloatParser {
   public static Float getFloatByString(String text) {
      Float f = new Float();
      StringCursor stringcursor = new StringCursor(text);
      char c0 = ' ';
      float f1 = 0.0F;
      float f2 = 0.0F;
      float f3 = 0.0F;
      float f4 = 0.0F;

      while (stringcursor.check3()) {
         if (stringcursor.check()) {
            c0 = stringcursor.getChar();
         } else if (c0 == ' ') {
            break;
         }

         switch (c0) {
            case 'C':
            case 'c':
               while (stringcursor.check2()) {
                  float f13 = stringcursor.getFloat();
                  float f15 = stringcursor.getFloat();
                  float f16 = stringcursor.getFloat();
                  float f8 = stringcursor.getFloat();
                  float f9 = stringcursor.getFloat();
                  float f10 = stringcursor.getFloat();
                  if (c0 == 'c') {
                     f13 += f1;
                     f15 += f2;
                     f16 += f1;
                     f8 += f2;
                     f9 += f1;
                     f10 += f2;
                  }

                  f.curveTo(f13, f15, f16, f8, f9, f10);
                  f1 = f9;
                  f2 = f10;
               }
               break;
            case 'H':
            case 'h':
               while (stringcursor.check2()) {
                  float f12 = stringcursor.getFloat();
                  if (c0 == 'h') {
                     f12 += f1;
                  }

                  f.lineTo(f12, f2);
                  f1 = f12;
               }
               break;
            case 'L':
            case 'l':
               while (stringcursor.check2()) {
                  float f11 = stringcursor.getFloat();
                  float f14 = stringcursor.getFloat();
                  if (c0 == 'l') {
                     f11 += f1;
                     f14 += f2;
                  }

                  f.lineTo(f11, f14);
                  f1 = f11;
                  f2 = f14;
               }
               break;
            case 'M':
            case 'm':
               boolean flag = true;

               while (stringcursor.check2()) {
                  float f6 = stringcursor.getFloat();
                  float f7 = stringcursor.getFloat();
                  if (c0 == 'm') {
                     f6 += f1;
                     f7 += f2;
                  }

                  if (flag) {
                     f.moveTo(f6, f7);
                     f3 = f6;
                     f4 = f7;
                     flag = false;
                  } else {
                     f.lineTo(f6, f7);
                  }

                  f1 = f6;
                  f2 = f7;
               }
               break;
            case 'V':
            case 'v':
               while (stringcursor.check2()) {
                  float f5 = stringcursor.getFloat();
                  if (c0 == 'v') {
                     f5 += f2;
                  }

                  f.lineTo(f1, f5);
                  f2 = f5;
               }
               break;
            case 'Z':
            case 'z':
               f.closePath();
               f1 = f3;
               f2 = f4;
               break;
            default:
               throw new IllegalArgumentException();
         }
      }

      return f;
   }
}
