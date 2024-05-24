package aed;

import java.util.*;

// Todos los tipos de datos "Comparables" tienen el método compareTo()
// elem1.compareTo(elem2) devuelve un entero. Si es mayor a 0, entonces elem1 > elem2
public class ABB<T extends Comparable<T>> implements Conjunto<T> {
    public class Nodo {
        Nodo next, prev, parent;
        T data;
        public Nodo(T data, Nodo parent){
            this.data = data;
            this.parent = parent;
        }

        @Override
        public String toString() {
            ArrayList<Nodo> visited = new ArrayList<Nodo>();
            return repr(this, visited);
        }

        private String repr(Nodo n, List<Nodo> visited){
            if (n == null){
                return "";
            }
            if (visited.contains(n)){
                return String.format("<%d:REC>", n.data);
            }
            visited.add(n);
            return String.format("<%d:%s:%s>", n.data, repr(n.prev, visited), repr(n.next, visited));
        }
    }
    Nodo root;

    private int cardinal(Nodo n){
        if (n == null){
            return 0;
        }
        return 1 + cardinal(n.next) + cardinal(n.prev);
    }

    private boolean pertenece(Nodo n, T elem){
        if (n == null)
            return false;
        int res = elem.compareTo(n.data);
        
        if(res ==  0){
            return true;
        }

        if(res >  0){
            return pertenece(n.next, elem);
        }
        
        return pertenece(n.prev, elem);
    }

    private void insertar(Nodo n, T elem){
        int res = elem.compareTo(n.data);
        
        if(res ==  0){
            return;
        }

        if(res >  0){
            if(n.next == null){
                n.next = new Nodo(elem, n);
            } else {
                insertar(n.next, elem);
            }
            return;
        }
        
        if(n.prev == null){
            n.prev = new Nodo(elem, n);
        } else{
            insertar(n.prev, elem);
        }
    }

    private Nodo minimo(Nodo n){
        if (n.prev == null){
            return n;
        }
        return minimo(n.prev);
    }

    private Nodo maximo(Nodo n){
        if (n.next == null){
            return n;
        }
        return maximo(n.next);
    }

    private void eliminar(Nodo n, T elem){
        int res = elem.compareTo(n.data);
        
        if(res ==  0){
            if(n.next == null && n.prev == null){
                Nodo m = n.parent;
                n.parent = null;
                if(m.next == n){
                    m.next = null;
                }
                if(m.prev == n){
                    m.prev = null;
                }
                return;
            }

            if(n.prev == null){
                Nodo m = n.parent;
                n.parent = null;
                if(m.next == n){
                    m.next = n.next;
                }
                if(m.prev == n){
                    m.prev = n.next;
                }
                return;
            }

            if(n.next == null){
                Nodo m = n.parent;
                n.parent = null;
                if(m.next == n){
                    m.next = n.prev;
                }
                if(m.prev == n){
                    m.prev = n.prev;
                }
                return;
            }

            Nodo min = minimo(n.next);
            n.data = min.data;
            Nodo m = min.parent;
            min.parent = null;
            if(m.prev == min){
                m.prev = null;
            } else if(m.next == min){
                m.next = null;
            }
        }

        if(res >  0){
            if(n.next == null){
                return;
            }
            eliminar(n.next, elem);
        }

        if(n.prev == null){
            return;
        }
        eliminar(n.prev, elem);
    }

    public ABB() {
    }

    public int cardinal() {
        return cardinal(root);
    }

    public T minimo(){
        return minimo(root).data;
    }

    public T maximo(){
        return maximo(root).data;
    }

    public void insertar(T elem){
        if(root == null){
            root = new Nodo(elem, null);
            return;
        }
        insertar(root, elem);
    }

    public boolean pertenece(T elem){
        return pertenece(root, elem);
    }

    public void eliminar(T elem){
        eliminar(root, elem);
    }

    @Override
    public String toString(){
        ArrayList<String> list = new ArrayList<>();
        Iterador<T> it = iterador();
        while(it.haySiguiente()){
            list.add(String.valueOf(it.siguiente()));
        }
        return "{" + String.join(",", list) + "}";
    }


    Nodo sucesor(Nodo n){
        if(n == null) return null;
        if(n.next != null){
            return minimo(n.next);
        }

        while(n.parent != null && n.parent.prev != n){
            n = n.parent;
        }

        return n.parent;
    }

    private class ABB_Iterador implements Iterador<T> {
        private Nodo _actual = minimo(root);

        public boolean haySiguiente() {
            return _actual != null;
        }
    
        public T siguiente() {
            T res = _actual.data;
            _actual = sucesor(_actual);
        
            return res;
        }
    }

    public Iterador<T> iterador() {
        return new ABB_Iterador();
    }

}
